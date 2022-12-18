<template>
<div class="cmpfather">
    <div class="form_area">
        
        <el-row>
            <el-col :span="6">
                    <el-card v-if="username" style="width:50%; margin-left: 40%;">
                        <div>
                            <img :src="(rootUrl + '/images' + avatar)" class="my_image">
                        </div>
                    </el-card>
            </el-col>
            <el-col :span="18" style="display: flexbox;" >
                <div  style="margin-left: 0%; ">
                    <h3 style="">Name &nbsp: &nbsp{{username}}</h3>
                    <h3 style="margin-top: 3%;">User id &nbsp: &nbsp{{userid}}</h3>
                    <h3 style="margin-top: 3%;">Tastes&nbsp  &nbsp: &nbspsweet food</h3>
                </div>
            </el-col>
        </el-row>
    </div>
</div>
</template>

<script>
export default {
	created(){
        this.username =  window.sessionStorage.getItem("username");
        this.userid =  window.sessionStorage.getItem("userid");
        this.avatar =  window.sessionStorage.getItem("avatar");
    },
    data(){
        return{
            rootUrl :this.$server_url,
            username:false,
            userid:"",
            avatar:"",
        }
    },
    methods:{
        
    }
}
</script>


<style lang="less" scoped>
.cmpfather {
  display: flex;
  justify-content: center;

  .form_area {
    margin-top: 50px;
    width: 100%;
    height: 40%;

    .my_image {
      object-fit: contain;
      width: 95%;
      height: auto;
      margin: 2.5%;
    }
  }
}
</style>