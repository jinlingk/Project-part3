<template>
    <el-row>
        <el-col v-for="item in imageList" :key="item.id">
            <el-card :ref="item" :body-style="{ padding: '0px' }" class="card">
                <div class="upper">
                    <img :src="item.img" class="image" />
                </div>
                <div class="upper">
                        <span class = "title_text">{{item.name}}</span>
                        <p>{{item.desc}}</p>
                    </div>
                <div style="padding: 14px;" class="lower">
                    <div class="bottom clearfix">
                        <time class="time">{{ item.tag }}</time>
                        <el-button type="success" icon="el-icon-caret-right" class="button" @click="dialogFormVisible = true">我是新界面</el-button>
                    </div>
                </div>
            </el-card>
        </el-col>
        <el-dialog title="Container operation parameters" :visible.sync="dialogFormVisible">
            <el-form :model="form">
				<!-- <span>{{this.$refs.item.name}}</span> -->
                <el-form-item label="Container name" :label-width="formLabelWidth">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="zone of activity" :label-width="formLabelWidth">
                    <el-select v-model="form.region" placeholder="Please select the activity area">
                        <el-option label="area one" value="shanghai"></el-option>
                        <el-option label="area two" value="beijing"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">Cancel</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">Sure</el-button>
            </div>
        </el-dialog>
    </el-row>
</template>

<script>
export default {
    created() {
        this.access = this.$route.params.access;
        console.log(this.access);
    },
    data() {
        return {
			dialogFormVisible: false,
            access: "detialed",
            // imageList: [],
			form:{
				name: "",
				region: "",
			},
			formLabelWidth: '120px',
            imageList: [
                {
                    id: 1,
                    name: "ubuntu",
                    tag: "latest",
                    img: "https://d1q6f0aelx0por.cloudfront.net/product-logos/library-ubuntu-logo.png",
                    desc: "Ubuntu is a Debian-based Linux operating system based on free software.",
                },
                {
                    id: 2,
                    name: "ubuntu",
                    tag: "latest",
                    img: "https://d1q6f0aelx0por.cloudfront.net/product-logos/library-ubuntu-logo.png",
                    desc: "Ubuntu is a Debian-based Linux operating system based on free software.",
                },
            ],
        };
    },
};
</script>

<style lang="less" scoped>
.card {
    position: relative;
    .upper {
        .title_text{
            font-size: 20px;
            font-weight: bold;
        }
        position: relative;
        .image {
            display: inline-block;
            width: 120px;
            height: 120px;
            margin: 10px;
        }
        .desc {
            display: inline-block;
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
        height: 150px;
    }
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
}
</style>