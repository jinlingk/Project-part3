<template>
  <div class="cmpfather">
    <div class="form_area">
      <el-card v-if="recipeInfo.images">
        <div slot="header" class="header">
          <span style="margin-left: 2%; font-size: large;">{{ recipeInfo.title }}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            serving:{{ recipeInfo.serving }}</span>
        </div>
        <div >
          <img :src="rootUrl + '/images' + recipeInfo.images" class="my_image">
        </div>

      </el-card>
      <el-card style="height: fit-content;margin-top: 20px;" v-if="ingredientsList[0]">
        <div v-for="item in ingredientsList" style="height: fit-content;">
          <p>Name:&nbsp&nbsp{{ item.name }}</p>
          <p>Purchaselink:&nbsp&nbsp{{ item.purchaselink }}</p>
          <p>Quantities:&nbsp&nbsp{{ item.quantities }}</p>
          <p>Allergen:&nbsp&nbsp{{ item.allergen == "true" ? "yes" : "no" }}</p>
          <el-divider></el-divider>
        </div>
      </el-card>
      <el-card style="height: fit-content;margin-top: 20px;" v-if="reviewsInfoList[0]">
        <div v-for="item in reviewsInfoList" style="height: fit-content;">
          <div>
            <h3 style="display: inline-block;">&nbsp&nbsp{{ item.reviewPostRequest.title }}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            </h3>
            <el-rate style="display: inline-block;" v-model="item.reviewPostRequest.rating" disabled show-score
              text-color="#ff9900">
            </el-rate>
            <p style="display: inline-block;  float:right ;margin-right: 5% ;">by:&nbsp&nbsp{{ item.username }}</p>

          </div>

          <p>&nbsp&nbsp{{ item.reviewPostRequest.content }}</p>
          <el-divider></el-divider>
        </div>
      </el-card>
      <el-card style="height: fit-content;margin-top: 20px;">
        <div style="height: fit-content;">
          <p>&nbsp&nbsp&nbspGive your rating and write your comments</p>
          <div style="margin-left: 10px;">
            <p style="display: inline-block;">&nbspRate&nbsp&nbsp&nbsp</p>
            <el-rate v-model="value1" style="display: inline-block;"></el-rate>
            <el-input v-model="input_title" placeholder="please input title"></el-input>
            <el-input style="margin-top: 9px;" type="textarea" :rows="2" placeholder="please input content" v-model="input_content">
            </el-input>
            <el-button type="info"  style="float:right;margin: 10px;" @click="commit()" >Commit</el-button>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {

  created() {
    this.recipeId = window.sessionStorage.getItem("recipeId");
    this.userid = window.sessionStorage.getItem("userid");
    this.$http.get("getRecipeIngredientInfo?" + "recipeId=" + this.recipeId).then(res => {
      let data = res.data;

      if (data) {
        console.log("data:", data);
        this.recipeInfo = data.recipeInfo
        this.ingredientsList = data.ingredientsList
        this.reviewsInfoList = data.reviewsInfoList
        return
      } else {


      }
    }).catch(error => {
      this.$message.error('failed to find information');
      console.log(error);
    });
  },

  data() {
    return {
      userid:"",
      //评分组件
      value1: 0,
      //输入标题
      input_title:"",
      input_content:"",

      rootUrl: this.$server_url,
      recipeId: "",
      recipeInfo: {},
      ingredientsList: [],
      reviewsInfoList: [],
    };
  },
  methods: {
    commit(){
            this.$http.post("postReview", JSON.stringify({
              userid:this.userid,
              title:this.input_title,
              content:this.input_content,
              rating:parseInt(this.value1),
              recipeid:this.recipeId
				})).then(response => {
          console.log(response.data);
					if (response.data.msg=="success") {
						this.new_success = true
						this.$message.success('commit success');
            this.$router.go(0)
            
					} else {
						this.$message.error('commit failed');
					}
					
				})
        },
  }
}
</script>

<style lang="less" scoped>
.cmpfather {
  display: flex;
  justify-content: center;

  .form_area {
    margin-top: 50px;
    width: 70%;
    height: auto;

    .my_image {
      object-fit: contain;
      width: 95%;
      height: auto;
      margin: 2.5%;
    }
  }
}
</style>