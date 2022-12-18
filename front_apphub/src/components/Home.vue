<template>
    <el-container class="home-container">
        <!-- head -->
        <el-header>
            <span class="header_text">Cookzilla</span>
           
            <el-dropdown>
                <el-avatar :src="this.avatar_url">
                </el-avatar>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-plus" @click.native="logout()">logout</el-dropdown-item>
                    <!-- <el-dropdown-item icon="el-icon-s-tools">Modify account information</el-dropdown-item> -->

                </el-dropdown-menu>
            </el-dropdown>
        </el-header>
        <el-container>
            <!-- side -->
            <el-aside :width="isCollapse ?'64px':'200px'">
                <div class="toggle-button" @click="toggleCollapase()">|||</div>
                <el-menu
                    default-active="1.4.1" class="el-menu-vertical-demo menu-custom" :collapse="isCollapse" :router="true">
                    <el-menu-item index="/image/public">
                        <i class="el-icon-food"></i>
                        <span slot="title">Delicious Recipes</span>
                    </el-menu-item>
                    <el-menu-item index="/image/Welcome">
                        <i class="el-icon-user"></i>
                        <span slot="title">My Infomation</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <!-- main window -->
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
export default {
    created() {
        this.rootUrl = this.$server_url;
        // console.log(this.access);
        this.username =  window.sessionStorage.getItem("username");
        		let data;
				this.$http.get("getRecipeInfo/0",{
                    headers: { username: this.username },
                }).then(res => {
					data = res.data;
                    this.avatar = data.avatar;
					window.sessionStorage.setItem("avatar", this.avatar);

                    this.avatar_url = this.rootUrl+'/images'+this.avatar;
                    //console.log("avatar",this.rootUrl+'/images'+this.avatar)
				}).catch(error => {
					console.log(error);
				});

    },
    data() {
        return {
            avatar_url:"",
            rootUrl : '',
            avatar:'',
            menuList: [],
            iconsObject: {
                100: "iconfont iconguanliyuan",
                200: "iconfont iconsport",
                101: "iconfont icondenglu",
                102: "iconfont iconmima",
                103: "iconfont iconsport",
                104: "iconfont iconshangpin",
                201: "iconfont iconshu",
                202: "iconfont iconkaluli",
                203: "iconfont iconshiwu",
                204: "iconfont icondenglu",
            },
            containers: [],
            isCollapse: false,
            activePath: "/image/public",
            label: true,
        };
    },
    methods: {
        logout() {
            //console.log("djklasjdklas");
            window.sessionStorage.clear();
            this.$router.push({ path: "/login" });
        },
        list_continers() {
            const token = window.sessionStorage.getItem("token");
            this.$http
                .get("docker/listContainers", {
                    headers: { token: token },
                })
                .then((res) => {
                    this.containers = res.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        toggleCollapase() {
            this.isCollapse = !this.isCollapse;
        },
    },
};
</script>

<style lang="less" scoped>
.lower {
        position: relative;
        .time {
            display: inline-block;
            float: left;
            font-size: 15px;
            color: #999;
        }
        .button {
            display: inline-block;
            position: relative;
            float: right;
            bottom: 10px;
        }
    }
.el-header {
    background-color: #b3c0d1;
    display: flex;
    justify-content: space-between; // right and left side
    padding-left: 0%; // left side
    align-items: center; // horizontal
    color: #fff;
    font-size: 20px;
    > div {
        //left side div 
        display: flex;
        align-items: center;
        span {
            margin-left: 15px;
        }
    }
}
.el-aside {
    background-color: #d3dce6;
    .el-menu {
        border-right: none; // Align right border
    }
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    background: #d3dce6;
    // min-height: 400px;
}
.menu-custom {
    background: #d3dce6;
}
.toggle-button {
    background-color: #4a5064;
    font-size: 10px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer; // display mouse pointer
}
.home-container {
    height: 100%;
    .header_text{
    margin-left: 40px;}
}

</style>
