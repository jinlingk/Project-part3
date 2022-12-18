<template>
	<div class="login_container">
		<!-- Logo -->
		<div class="logo">
			<img src="../assets/img/docker.png" alt />
		</div>
		<div class="login_box" :style="{height: loginbox_h}">
			<div class="login_icon">
				<img src="../assets/img/tilamisu.png">
			</div>
			<div class="login_text">
				<h3>login</h3>
			</div>
			<!-- form area -->
			<el-form ref="loginFormRef" :rules="loginRules" :model="loginForm" class="login_form" label-width="0">
				<!-- username -->
				<el-form-item prop="username" inline="true">
					<el-row :gutter="24">
						<el-col :span="passwordFlag?24:19">
							<el-input v-model="loginForm.username" prefix-icon="iconfont icon-user">
							</el-input>
						</el-col>
						<el-col v-if="!passwordFlag" :span="5">
							<el-button type="text" class="username_btn" icon="el-icon-check" @click="checkUsername()">next step</el-button>
						</el-col>
					</el-row>
				</el-form-item>
				<!-- pasword -->
				<el-form-item v-if="passwordFlag" prop="password">
					<el-row :gutter="24">
						<el-col :span="passwordFlag?24:19">
							<el-input v-model="loginForm.password" prefix-icon="iconfont icon-password" :type="flag?'text':'password'">
								<i slot="suffix" :class="flag? 'iconfont icon-yanjing_yincang': 'iconfont icon-yanjing_xianshi'" @click="flag=!flag"/>
							</el-input>
						</el-col>
					</el-row>
				</el-form-item>
				<!-- submit button -->
				<el-form-item v-if="passwordFlag" class="submit_btn">
					<el-button type="primary" @click="submit()">submit</el-button>
					<el-button type="info" @click="reset()">reset</el-button>
				</el-form-item>
			</el-form>
		</div>
		<div class="signup_link">
			<p v-if="resetPasswordFlag">
				<a href="/foundPassword">forget password？</a>
			</p>
			<p v-if="signupFlag">
				Don't have an account yet?				
				<a href="/signup">register now!</a>
			</p>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			loginForm: {
				username: '',
				password: ''
			},
			// Check rules for login form data
			loginRules: {
				username: [
					{ required: true, message: 'enter one user name', trigger: 'blur' },
            		// { min: 3, max: 5, message: 'lengthe 3 to 5 characters', trigger: 'blur' }
				],
				password: [
					{ required: true, message: 'Please input a password', trigger: 'blur' },
            		// { min: 3, max: 5, message: 'length 3 to 5 characters', trigger: 'blur' }
				],
			},
			flag: false,
			passwordFlag: false,
			resetPasswordFlag: false,
			signupFlag: false,
			loginbox_h: '200px',
		}
	},
	methods: {

		// reset
		reset(){
			this.$refs.loginFormRef.resetFields();
			this.flag = false;
			this.passwordFlag = false;
			this.resetPasswordFlag = false;
			this.signupFlag = false;
			this.loginbox_h = '200px';
		},
		// submit
		submit(){
			this.$refs.loginFormRef.validate(async valid =>{
				if(!valid) return;
				let data;
				this.$http.post("auth", JSON.stringify({
					username: this.loginForm.username,
					password: this.$sha224(this.loginForm.password),
				})).then(res => {
					data = res.data;
					if(data.msg == "success"){
						this.$message.success('Login succeeded');
						window.sessionStorage.setItem("username", this.loginForm.username);
						window.sessionStorage.setItem("token", data.token);
						window.sessionStorage.setItem("userid", data.userId);
						this.$router.push({path: "/home"});
					}else{
						this.$message.error('Login failed');
					}
					console.log(res.data.msg);
				}).catch(error => {
					console.log(error);
				});
			});
		},
		checkUsername(){
			if(this.loginForm.username == ""){
				return;
			}
			let isIn;
			this.$http.get("isIn?username="+this.loginForm.username).then(res => {
				isIn = res.data;
				console.log(isIn);
				if(isIn){
					this.passwordFlag = true;
					this.loginbox_h = '300px';
					this.resetPasswordFlag = true;
				}else{
					this.signupFlag = true;
					this.$message.warning('The user does not exist');
				}
			}).catch(error => {
				console.log(error);
			});

		},
	},
}
</script>

<style lang="less" scoped>
.login_container{
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

.login_box{
	display: block;
	width: 450px;
	background-color: #fff;
	border-radius: 3px;
	position: absolute;
	left: 50%;
	top: 55%;
	transform: translate(-50%, -50%);
	.login_icon{
		width: 80px;
		height: 80px;
		border-radius: 70%;
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
	.login_text{
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
	.login_form{
		position: absolute;	
		bottom: 0%;
		position: absolute;
		left: 50%;
		top: 60%;
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
