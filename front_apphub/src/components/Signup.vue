<template>
	<div class="signup_container">
		<!-- Logo -->
		<div class="logo">
			<img src="../assets/img/docker.png" alt />
		</div>
		<div class="signup_box" :style="{height: signupbox_h}">
			<div class="signup_icon">
				<img src="../assets/img/signup.png">
			</div>
			<div class="signup_text">
				<h3>Sign Up</h3>
			</div>
			<!-- form area -->
			<el-form ref="signupFormRef" :rules="signupRules" :model="signupForm" class="signup_form" label-width="0">
				<!-- username -->
				<el-form-item prop="username" inline="true">
					<el-row :gutter="24">
						<el-col :span="passwordFlag?24:19">
							<el-input v-model="signupForm.username" prefix-icon="iconfont icon-user" @focus="inputFocus()">
							</el-input>
						</el-col>
						<el-col v-if="!passwordFlag" :span="5">
							<el-button type="text" class="username_btn" icon="el-icon-check" @click="checkUsername()">下一步</el-button>
						</el-col>
					</el-row>
				</el-form-item>
				<!-- pasword -->
				<el-form-item v-if="passwordFlag" prop="password">
					<el-row :gutter="24">
						<el-col :span="passwordFlag?24:19">
							<el-input v-model="signupForm.password" prefix-icon="iconfont icon-password" :type="flag?'text':'password'" @focus="focusPassword()">
								<i slot="suffix" :class="flag? 'iconfont icon-yanjing_yincang': 'iconfont icon-yanjing_xianshi'" @click="flag=!flag"/>
							</el-input>
						</el-col>
					</el-row>
				</el-form-item>
				<el-form-item v-if="passwordFlag" prop="email">
					<el-row :gutter="24">
						<el-col :span="passwordFlag?24:19">
							<el-input v-model="signupForm.email" prefix-icon="el-icon-s-promotion" type="text" @focus="focusEmail()">
								<!-- <i slot="suffix" :class="flag? 'iconfont icon-yanjing_yincang': 'iconfont icon-yanjing_xianshi'" @click="flag=!flag"/> -->
							</el-input>
						</el-col>
					</el-row>
				</el-form-item>

				<el-form-item v-if="passwordFlag" prop="phone">
					<el-row :gutter="24">
						<el-col :span="passwordFlag?24:19">
							<el-input v-model="signupForm.phone" prefix-icon="el-icon-phone" type="text" @focus="focusPhone()">
								<!-- <i slot="suffix" :class="flag? 'iconfont icon-yanjing_yincang': 'iconfont icon-yanjing_xianshi'" @click="flag=!flag"/> -->
							</el-input>
						</el-col>
					</el-row>
				</el-form-item>
				
				<!-- avatar uploader -->
				<el-form-item v-if="passwordFlag" prop="shuju">
					<el-row :gutter="24">
						<el-col :span="passwordFlag?24:19">
							<el-upload ref="upload" class="upload-demo" action="#" :http-request="UploadImage" >
								<!--                :before-upload="beforeUpload"-->
								<el-button size="small" type="primary" class="el-icon-upload">&nbsp;&nbsp;数据上传</el-button>
								<!--                <div-->
								<!--                  slot="tip"-->
								<!--                  class="el-upload__tip"-->
								<!--                >只能上传db文件，且不超过一个</div>-->
							</el-upload>
						</el-col>
					</el-row>
				</el-form-item>
				
				
				<!-- submit button -->
				<el-form-item v-if="passwordFlag" class="submit_btn">
					<el-button type="primary" @click="submit()">submit</el-button>
					<!-- <el-button type="info" @click="reset()">reset</el-button> -->
				</el-form-item>
			</el-form>
			
		</div>
		<div class="signup_link">
			<p v-if="signupFlag">
				Already have an account?				
				<a href="/login">login now!</a>
			</p>
		</div>
	</div>
</template>

<script>
import axios from 'axios'
export default {
	data() {

		var checkEmail = (rule, value, callback) => {
		const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
		if (!value) {
			return callback(new Error('The mailbox cannot be empty'))
		}
		setTimeout(() => {
			if (mailReg.test(value)) {
				callback()
			} else {
				callback(new Error('Please enter the correct email format'))
			}
		}, 100)
	};
		return {
			isok : 0,
			signupForm: {
				username: 'enter the username',
				password: 'enter the password',
				email: 'enter the e-mail',
				phone: 'please input phone num',
				avatar: '', 
			},
			// Verification rules of signup form data

			signupRules: {
				username: [
					{ required: true, message: 'enter the username', trigger: 'blur' },
            		{ min: 3, max: 25, message: 'length 3 to 25 characters', trigger: 'blur' }
				],
				password: [
					{ required: true, message: 'enter the password', trigger: 'blur' },
            		{ min: 3, max: 25, message: 'length 3 to 25 characters', trigger: 'blur' }
				],
				email: [
					// { required: true, message: 'enter the e-mail', trigger: 'blur' },
					{required: true, validator: checkEmail, trigger: 'blur'}
				],
			},
			flag: true,
			passwordFlag: false,
			resetPasswordFlag: false,
			signupFlag: false,
			signupbox_h: '200px',
		}
	},
	methods: {
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
						this.isok = 1 // Uploading succeeded: 1 succeeded 0 failed
						this.signupForm.avatar = response.data.path
						this.$message.success('upload success');
						console.log(this.signupForm)
					} else {
						this.isok = 0
						this.$message.error('upload failed');
					}
					
				})
			}
		},


		// submit
		submit() {
			this.$refs.signupFormRef.validate(async valid => {
				if (!valid) return;
				// let data;
				// this.$http.post("register", JSON.stringify({
				// 	username: this.signupForm.username,
				// 	passwordMd5: this.$md5(this.signupForm.password),
				// 	email: this.signupForm.email,
				// })).then(res => {
				// 	data = res.data;
				// 	if (data.msg == "success") {
				// 		this.$message.success('registration success');
				// 		this.$router.push({ path: "/login" });
				// 	} else {
				// 		this.$message.error('registration failed:' + data.msg);
				// 	}
				// 	console.log(res.data.msg);
				// }).catch(error => {
				// 	console.log(error);
				// });
				const formdata = new FormData()
				formdata.append('username', this.signupForm.username)
				formdata.append('password', this.$sha224(this.signupForm.password))
				formdata.append('email', this.signupForm.email)
				formdata.append('phone', this.signupForm.phone)
				formdata.append('avatar', this.signupForm.avatar)
				axios({
					url: this.$server_url+'/register',
					method: 'post',
					data: formdata,
					headers: { 'Content-Type': 'multipart/form-data' },
				}).then(response => {
					console.log(response.data)
					if (response.data.msg=="success") {
						
						this.$message.success('upload success');
						this.$router.push({path: "/login"});
					} else {
						this.isok = 0
						this.$message.error('register failed');
					}
					
				})
			});
		},
		checkUsername() {
			if (this.signupForm.username == "") {
				return;
			}
			if (this.signupForm.username == "enter the username") {
				return;
			}
			let isIn;
			this.$http.get("isIn?username=" + this.signupForm.username).then(res => {
				isIn = res.data;
				console.log(isIn);
				if (!isIn) {
					this.passwordFlag = true;
					this.signupbox_h = '300px';
					this.resetPasswordFlag = true;
				} else {
					this.signupFlag = true;
					this.$message.warning('username is exist');
				}
			}).catch(error => {
				console.log(error);
			});
		},
		focusPassword(){
			if(this.signupForm.password == "enter the password"){
				this.signupForm.password = "";
				this.flag = false;
			}
		},
		inputFocus(){
			if(this.signupForm.username == "enter the username"){
				this.signupForm.username = "";
			}
		},
		focusEmail(){
			if(this.signupForm.email == "enter the e-mail"){
				this.signupForm.email = "";
			}
		},
		focusPhone(){
			if(this.signupForm.phone == "please input phone num"){
				this.signupForm.phone = "";
			}
		},

	},
}
</script>

<style lang="less" scoped>


// root format
.signup_container{
	// background color
	background-color: #2b4b6b;
	height: 100%;
}

.logo{
	width: 480px;
	height: 220px;	
	border-radius: 50%;
	padding: 10px;
	position: absolute;
	left: 50%;
	top: 20%;
	transform: translate(-50%, -50%);
	img{
			width: 100%;
			height: 100%;
			border-radius: 50%;
		}
}

.signup_box{
	display: block;
	width: 450px;
	background-color: #fff;
	border-radius: 3px;
	position: absolute;
	left: 50%;
	top: 55%;
	transform: translate(-50%, -50%);
	.signup_icon{
		width: 80px;
		height: 80px;
		border-radius: 50%;
		background-color: #fff;
		position: absolute;
		left: 50%;
		top: 0;
		transform: translate(-50%, -50%);
		img{
			width: 100%;
			height: 100%;
			border-radius: 50%;
		}
	}
	.signup_text{
		text-align: center;
		width: 80px;
		height: 80px;	
		position: absolute;
		left: 50%;
		top: 30%;
		transform: translate(-50%, -50%);
		// padding: 0;
		// border: 0;
		color: #2b4b6b;
	}
	.username_btn{
		height: 100%;
		position: relative;
	}
	.signup_form{
		position: absolute;
		left: 50%;
		top: 64%;
		transform: translate(-50%, -50%);	
		width: 100%;
		padding: 0 10px;
		box-sizing: border-box;
	}
}

.submit_btn{
	display: flex;
	justify-content: flex-end;
}


.signup_link{
	position: absolute;
	width: 450px;
	left: 50%;
	top: 75%;
	transform: translate(-50%, -50%);
	border: 0;
	padding: 10px;
	p{
		display: block;
		text-align: center;
		color: rgb(166, 163, 163);
		a{
			color: #b3c9e6;
		}
	}
}
</style>
