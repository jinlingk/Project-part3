<template>
<div>
    <el-row >
        <el-button style="margin-left: 4%;" type="primary"  @click="dialogFormVisible = true">new recipes</el-button>
        <el-dialog title="recipe's basic infomation" :visible.sync="dialogFormVisible" :close-on-click-modal="false">
            <el-form :model="form">
				<!-- <span>{{this.$refs.item.name}}</span> -->
                <el-form-item label="name of the recipe" >
                    <el-input v-model="recipe.name" autocomplete="off" style="width: 70%;"></el-input>
                </el-form-item>
                <el-form-item label="serveing of the recipe" >
                    <el-input v-model="recipe.serveing" autocomplete="off" style="width: 70%;"></el-input>
                </el-form-item>
                <el-form-item label="img of the recipe" >
                    <el-upload ref="upload" class="upload-demo" action="#" :http-request="UploadImage" >
								<el-button size="small" type="primary" class="el-icon-upload">&nbsp;&nbsp;upload image</el-button>
							</el-upload>
                </el-form-item>
                
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">exit</el-button>
                    <el-button type="primary" @click="recipe_submit()">click to add ingredients</el-button>
                </div>
        </el-dialog>
         <!-- This is the pop-up interface for adding raw materials -->
        <el-dialog title="ingredient infomation" :visible.sync="ingredientsFormVisible" :close-on-click-modal="false">
            <el-form :model="form">
				<!-- <span>{{this.$refs.item.name}}</span> -->
                <el-form-item label="name of the ingredient" >
                    <el-input v-model="ingredients.name" autocomplete="off" style="width: 70%;"></el-input>
                </el-form-item>
                <el-form-item label="quantities of the ingredient" >
                    <el-input v-model="ingredients.quantities" autocomplete="off" style="width: 70%;"></el-input>
                </el-form-item>
                <el-form-item label="purchaselink of the ingredient" >
                    <el-input v-model="ingredients.purchaselink" autocomplete="off" style="width: 70%;"></el-input>
                    </el-form-item>
                    <el-form-item label="allergen of the ingredient">
                        <el-select v-model="ingredients.allergen" placeholder="select">
                            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                            </el-option>
                        </el-select>
                </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="ingredients_submit()">complete recipe</el-button>
                    <el-button type="primary" @click="ingredients_submit2()">add more ingredients</el-button>
                </div>
        </el-dialog>

        <!-- Search box and search button here -->
        <el-input style="width: 20%;margin-left: 50%;" v-model="input_search" placeholder="find what you want"></el-input>
        <el-button style="margin-left: 2%;" type="primary" @click="search_button" icon="el-icon-search" circle ></el-button>
    </el-row>
    <el-row type="flex" class="el_row" >
        <el-col :span="8" v-for="item in recipeInfoList" :key="item.index" >
            <el-card :ref="item" :body-style="{ padding: '0px' }" class="card" >
                <div class="image_div" @click="jumpTodetail(item.id)">
                    <img :src="rootUrl+'/images'+item.images" class="my_image" />
                </div>
                <div class="title_text">
                        <span> Recipe name: {{item.title}}</span>
                        <p> Serving of recipe : {{item.serving}}</p>
                    </div>
            </el-card>
        </el-col>
        
    </el-row>
    <el-pagination
            v-if="pageshow"
            class="next_page"
            :current-page="currentPage"
            background
            :page-sizes= [1,10]
            layout=" prev, pager, next, jumper"
            :total="100"
            @current-change="handleCurrentChange"
          />

</div>
</template>

<script>
import axios from 'axios'
export default {
    created() {
        this.rootUrl = this.$server_url;
        this.access = this.$route.params.access;
        // console.log(this.access);
        this.username =  window.sessionStorage.getItem("username");
        		let data;
				this.$http.get("getRecipeInfo/"+this.currentPage,{
                    headers: { username: this.username },
                }).then(res => {
					data = res.data;
                    this.recipeInfoList = data.recipeInfoList;
                    //console.log(data);
					if (data.msg == "success") {
					} else {
					}
					//console.log(res.data.msg);
				}).catch(error => {
					console.log(error);
				});

    },
    data() {
        return {
            options: [{
                value: true,
                label: 'yes'
            }, {
                value: false,
                label: 'no'
            }],
            recipeId:"",
            ingredientsFormVisible : false,
            new_success :false,//Whether the interface for controlling raw materials is displayed
            recipe:{
                name:"",
                serveing:"",
                imageurl:""
            },
            ingredients:{
                name:"",
                quantities:"",
                purchaselink:"",
                allergen:false,
            }
            ,
            input_search:"",
            rootUrl : '',
            username:'',
            lastpage:0,
            currentPage:0,
            pageshow:true,
			dialogFormVisible: false,
            access: "ImageList",
            // imageList: [],
			form:{
				name: "",
				region: "",
			},
			formLabelWidth: '120px',
            recipeInfoList: [],
        };
    },
    methods:{
        //Click the image to jump to the details interface
        jumpTodetail(id){
            window.sessionStorage.setItem("recipeId", id);
            this.$router.push({path: "/image/Recipedetail"});
        },

        search_info(page,search_contain){
            if(this.input_search==""){
                this.$message.error("can not be empty")
            }
            else{
                this.$message.success("searching "+search_contain);
                this.pageshow = false
                let data;
				this.$http.get("searchRecipeInfo/"+page+"?"+"query="+search_contain).then(res => {
					data = res.data;
                    //console.log(data);
                   // console.log(data.length)
					if (data.length==0) {
                        //console.log("this.currentPage",this.currentPage);
                        this.$message.error('no more page');
                        this.currentPage = this.lastpage;
                        this.pageshow = true
                        return
					} else {
                        this.currentPage = 0
                        this.recipeInfoList = data.recipeInfoList
                        this.pageshow = true
						// this.$message.error('registration failed:' + data.msg);
					}
				}).catch(error => {
					console.log(error);
				});
            }
        },
        //search_button
        search_button(){
            if(this.input_search==""){
                this.$message.error("can not be empty")
            }
            else{
                this.search_info(0,this.input_search)
            }
        },

        
        //upload_ingredients info
        ingredients_submit(){
            this.$http.post("postIngredient", JSON.stringify({
                    recipeId:this.recipeId,
					ingredients:{
                        name:this.ingredients.name,
                        quantities:parseInt(this.ingredients.quantities),
                        purchaselink:this.ingredients.purchaselink,
                        allergen:this.ingredients.allergen
                    }
				})).then(response => {
					//console.log("response",response.data)
					if (response.data.msg=="success") {
						this.new_success = true
						this.$message.success('add success');
                        this.ingredientsFormVisible = false//Set the display material adding interface here
                        this.ingredients.name = null
                        this.ingredients.quantities = null
                        this.ingredients.purchaselink = null
                        this.ingredients.allergen = null
					} else {
						this.isok = 0
						this.$message.error('add failed');
					}
					
				})
        },
        ingredients_submit2(){
            this.$http.post("postIngredient", JSON.stringify({
                    recipeId:this.recipeId,
					ingredients:{
                        name:this.ingredients.name,
                        quantities:parseInt(this.ingredients.quantities),
                        purchaselink:this.ingredients.purchaselink,
                        allergen:this.ingredients.allergen
                    }
				})).then(response => {
					//console.log("response",response.data)
					if (response.data.msg=="success") {
						this.new_success = true
						this.$message.success('add success');
                        this.ingredientsFormVisible = true//Set the display material adding interface here
                        this.ingredients.name = null
                        this.ingredients.quantities = null
                        this.ingredients.purchaselink = null
                        this.ingredients.allergen = null
					} else {
						this.isok = 0
						this.$message.error('add failed');
					}
					
				})
        },
        //upload png
		UploadImage(param) {
			if (0) {
				this.$message({
					message: 'Please select a file type and upload it again',
					type: 'warning'
				})
				return
			} else if (0) {
				this.$message({
					message: 'Please select a district or county and upload again',
					type: 'warning'
				})
				return
			} else {
				// When uploading a new file, set the progress bar value to zero
				const formdata = new FormData()
				formdata.append('avatar', param.file)
				axios({
					url: this.$server_url+'/upload',
					method: 'post',
					data: formdata,
					headers: { 'Content-Type': 'multipart/form-data' },
				}).then(response => {
					if (response.data.msg=="success") {
						this.recipe.imageurl = response.data.path
						this.$message.success('upload success');
						//console.log("recipe_msg",this.recipe)
					} else {
						this.isok = 0
						this.$message.error('upload failed');
					}
					
				})
			}
		},
        // submit
		recipe_submit() {

				this.$http.post("postRecipeInfo", JSON.stringify({
					title: this.recipe.name,
					serveing: this.recipe.serveing,
                    image: this.recipe.imageurl
				})).then(response => {
					//console.log("response",response.data)
					if (response.data.msg=="success") {
                        this.recipeId = response.data.recipeid
						this.new_success = true
						this.$message.success('upload success');
						this.dialogFormVisible = false
                        this.ingredientsFormVisible = true//Set the display material adding interface here
                        this.ingredients.name = null
                        this.ingredients.quantities = null
                        this.ingredients.purchaselink = null
                        this.ingredients.allergen = null
					} else {
						this.isok = 0
						this.$message.error('new failed');
					}
					
				})
		},
        handleCurrentChange(val) {
           if(this.search_info ==""){
            this.pageshow = false
            let temp_currentPage = val - 1
            let data;
            this.$http.get("getRecipeInfo/" + temp_currentPage, {
                headers: { username: this.username },
            }).then(res => {
                data = res.data;
                //console.log(data);
                //console.log(data.length)
                if (data.length == 0) {
                    //console.log("this.currentPage", this.currentPage);
                    this.$message.error('no more page');
                    this.currentPage = this.lastpage;
                    this.pageshow = true
                    return
                    // this.$message.success('registration success');
                    // this.$router.push({ path: "/login" });
                } else {
                    this.currentPage = val
                    this.lastpage = val
                    this.recipeInfoList = data.recipeInfoList
                    this.pageshow = true
                }
            }).catch(error => {
                console.log(error);
            });
           }
           else{
                this.pageshow = false
                let page = val - 1
                //this.$message.success("searching "+this.input_search);
                this.pageshow = false
                let data;
				this.$http.get("searchRecipeInfo/"+page+"?"+"query="+this.input_search).then(res => {
					data = res.data;
                    //console.log(data);
                    //console.log(data.length)
					if (data.length==0) {
                        this.$message.error('no more search infomation of ' + this.input_search);
                        this.currentPage = this.lastpage;
                        this.pageshow = true
                        return
					} else {
                        this.currentPage = val
                        this.lastpage = val
                        this.recipeInfoList = data.recipeInfoList
                        this.pageshow = true
						// this.$message.error('registration failed:' + data.msg);
                        return
					}

				}).catch(error => {
                    this.pageshow = true
					console.log(error);
				});
           }
        },




    }
};
</script>

<style lang="less" scoped>
.next_page{
    margin-left: 35%;
    position: relative;
}
.el_row{
    display: flex;
    flex-wrap: wrap;
    .title_text{
            margin-left: 20px;
            font-size: 15px;
            font-weight: bold;
        }
}
.card {
    width: 80%;
    height: 90%;
    display: block;
    margin: 30px;
    position: relative;
    .image_div{
        
        display: flex;
        justify-content: center; 
        align-items: center; 
        .my_image {
        object-fit:cover;
        width: 100%;
        height: 300px;
        margin: 10px;
        }
    }
    .upper {
        .title_text{
            margin-left: 20px;
            font-size: 30px;
            font-weight: bold;
        }
        position: relative;
        .desc {
            display: block;
            // width: 120px;
            height: 120px;
            top: 0%;
            transform: translate(0%, -50%);
            span {
                font-size: 20px;
                font-weight: bold;
                position: relative;
                top: 0%;
                margin: 0px;
            }
        }
        height: 250px;
    }
    .lower {
        position: relative;
        .time {
            display: block;
            float: left;
            font-size: 15px;
            color: #999;
        }
        .button {
            display: inline-block;
            position: relative;
            bottom: 10px;
        }
    }
}
</style>