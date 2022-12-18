package edu.nyu.lk.cookzilla.controller;

import edu.nyu.lk.cookzilla.bean.request.*;
import edu.nyu.lk.cookzilla.bean.response.*;
import edu.nyu.lk.cookzilla.dao.UserInfoMapper;
import edu.nyu.lk.cookzilla.model.Ingredients;
import edu.nyu.lk.cookzilla.model.Reviews;
import edu.nyu.lk.cookzilla.model.UserInfo;
import edu.nyu.lk.cookzilla.services.AuthServices;
import edu.nyu.lk.cookzilla.services.FileUpAndDown;
import edu.nyu.lk.cookzilla.services.RecipeInfoServices;
import edu.nyu.lk.cookzilla.services.RegisterServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class CookzillaController {


    @Autowired
    private AuthServices authServices;

    @Autowired
    private RegisterServices registerServices;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private FileUpAndDown fileUpAndDown;

    @Autowired
    private RecipeInfoServices recipeInfoServices;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param authRequest
     * @return AuthResponse
     */
    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest authRequest){
        AuthResponse authResponse = new AuthResponse();

        /**
         * check request body
         */
        if(authRequest.getUsername() == null || authRequest.getPassword() == null){
            authResponse.setToken("");
            authResponse.setMsg("missing parameters");
            logger.error("[ Cookzolla ]" + authRequest.getUsername() + " login failer, error message: " + authResponse.getMsg());
            return authResponse;
        }

        String token = authServices.authByUsernameAndPassword(authRequest);
        /**
         * get user info by username
         */
        if (token == null)
        {
            authResponse.setToken("");
            authResponse.setMsg("auth failed");
            logger.error("[ Cookzolla ]" + authRequest.getUsername() + " login failer, error message: " + authResponse.getMsg());
            return authResponse;
        }
        else
        {
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(authRequest.getUsername());
            authResponse.setUserId(userInfo.getUserid().longValue());
            authResponse.setToken(token);
            authResponse.setMsg("success");
            logger.info("[ Cookzolla ]" + authRequest.getUsername() + " login success");
            return authResponse;
        }
    }

    /**
     * check if user is exist
     * @param username
     * @return boolean
     */
    @GetMapping("/isIn")
    public String isIn(@RequestParam("username") String username) {
        boolean isIn = authServices.isIn(username);
        if(isIn){
            logger.info("[ Cookzolla ]" + username + " does exist ");
            return "true";
        }
        else{
            logger.error("[ Cookzolla ]" + username + " does not exist ");
            return "false";
        }
    }


    /**
     * 注册账号
     * @param registerRequests
     * @return
     */
//    todo:
    @PostMapping("/register")
    public RegisterResponse register(@RequestParam(value = "username",required = true) String username,
                                     @RequestParam(value = "password",required = true) String password,
                                     @RequestParam(value = "email", required = false, defaultValue = "") String email,
                                     @RequestParam(value = "phone", required = false, defaultValue = "") String phone,
                                     @RequestParam(value = "avatar", required = false, defaultValue = "") String file) throws Exception {
        RegisterResponse registerResponse = new RegisterResponse();

        /**
         * check username and password
         */
        if(username == null || password == null){
            registerResponse.setCode(400);
            registerResponse.setMsg("Bad Request, username or password can not be empty!");
            logger.error("[ Cookzolla ]" + username + " register failed, error message: " + registerResponse.getMsg());
            return registerResponse;
        }

        /**
         * check the length of the password, sha224 len 56
         */
        if(password.length()!=56){
            registerResponse.setCode(400);
            registerResponse.setMsg("Bad Request, password must be sha-2 format!");
            logger.error("[ Cookzolla ]" + username + " register failed, error message: " + registerResponse.getMsg());
            return registerResponse;
        }

        /**
         * build userInfo instance
         */
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        if(email != null){
            userInfo.setEmail(email);
        }
        if(phone != null){
            userInfo.setPhone(phone);
        }
        if(file != null){
            userInfo.setAvatar(file);
        }

        /**
         * try to insert userInfo into db
         */
        try {
            if(registerServices.createAccount(userInfo)){
                registerResponse.setCode(200);
                registerResponse.setMsg("success");
                logger.info("[ Cookzolla ]" + username + " register success");
                return registerResponse;
            }
            else {
                registerResponse.setCode(500);
                registerResponse.setMsg("Internal Server Error, create account failed!");
                logger.error("[ Cookzolla ]" + username + " register failed, error message: " + registerResponse.getMsg());
                return registerResponse;
            }
        }
        catch (Exception e){
            registerResponse.setCode(400);
            registerResponse.setMsg("Bad request! parameter error");
            logger.error("[ Cookzolla ]" + username + " register failed, error message: " + registerResponse.getMsg());
            return registerResponse;
        }
    }

    /**
     * upload pictures
     */
    @PostMapping("/upload")
    public UploadResponse upload(@RequestParam(value = "avatar", required = true) MultipartFile file) throws Exception {
        UploadResponse uploadResponse = new UploadResponse();
        Map<String, Object> resMap = fileUpAndDown.uploadPicture(file);
        if(resMap.get("result").equals("success")){
            uploadResponse.setPath((String) resMap.get("path"));
            uploadResponse.setMsg((String) resMap.get("result"));
            logger.info("[ Cookzolla ]" + " upload image success, image path: " + uploadResponse.getPath());
        }
        else{
            uploadResponse.setMsg((String) resMap.get("result"));
            logger.error("[ Cookzolla ]" + " upload image failed, error message: " + uploadResponse.getMsg());
        }
        return uploadResponse;
    }

    @GetMapping("/getRecipeInfo/{page}")
    public RecipeInfoListResponse getRecipeInfoList(@PathVariable("page") Integer page, @RequestHeader("username") String username){
        RecipeInfoListResponse recipeInfoListResponse = new RecipeInfoListResponse();
        List<RecipeInfo> recipeInfoList = recipeInfoServices.getRecipeInfoList(page);
        recipeInfoListResponse.setRecipeInfoList(recipeInfoList);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(username);
        recipeInfoListResponse.setAvatar(userInfo.getAvatar());
        recipeInfoListResponse.setLength(recipeInfoList.size());
        logger.info("[ Cookzolla ]" + username + " get recipe info, length: " + recipeInfoList.size() );
        return recipeInfoListResponse;
    }

    @PostMapping("/postRecipeInfo")
    public BaseResponse postRecipeInfo(@RequestBody PostRecipeInfoRequest postRecipeInfoRequest){
        BaseResponse baseResponse;
        baseResponse = recipeInfoServices.insertRecipe(postRecipeInfoRequest);
        if(baseResponse.getMsg().equals("success")){
            baseResponse.setCode(200);
            logger.info("[ Cookzolla ]" + " post recipe success");
        }else{
            baseResponse.setCode(500);
            logger.error("[ Cookzolla ]" + " post recipe failed, error message: " + baseResponse.getMsg());
        }
        return baseResponse;
    }

    @PostMapping("/postIngredient")
    public BaseResponse postIngredient(@RequestBody IngredientRequest ingredientRequest){
        BaseResponse baseResponse = new BaseResponse();
        Ingredients ingredients = ingredientRequest.getIngredients();
        Integer recipeId = ingredientRequest.getRecipeId();
        String result = recipeInfoServices.addIngredient(ingredients, recipeId);
        baseResponse.setMsg(result);
        if(result.equals("success")){
            baseResponse.setCode(200);
            logger.info("[ Cookzolla ]" + " post ingredient success");
        }else{
            baseResponse.setCode(500);
            logger.error("[ Cookzolla ]" + " post ingredient failed, error message: " + baseResponse.getMsg());
        }
        return baseResponse;
    }

    @GetMapping("/searchRecipeInfo/{page}")
    public RecipeInfoListResponse searchRecipeInfo(@RequestParam String query, @PathVariable Integer page){
        RecipeInfoListResponse recipeInfoListResponse = new RecipeInfoListResponse();
        List<RecipeInfo> recipeInfoList = recipeInfoServices.searchRecipe(query, page);
        recipeInfoListResponse.setRecipeInfoList(recipeInfoList);
        recipeInfoListResponse.setLength(recipeInfoList.size());
        return recipeInfoListResponse;
    }

    @GetMapping("/getRecipeIngredientInfo")
    public RecipeIngredientResponse getRecipeIngredientInfo(@RequestParam Integer recipeId){
        RecipeIngredientResponse recipeIngredientResponse;
        recipeIngredientResponse = recipeInfoServices.getRecipeIngredient(recipeId);
        return recipeIngredientResponse;
    }

    @PostMapping("postReview")
    public BaseResponse postReview(@RequestBody ReviewPostRequest reviewPostRequest){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setRecipeid(reviewPostRequest.getRecipeid());
        String result = recipeInfoServices.postReview(reviewPostRequest);
        baseResponse.setMsg(result);
        if (result.equals("success")){
            baseResponse.setCode(200);
        }else{
            baseResponse.setCode(500);
        }
        return baseResponse;
    }
}
