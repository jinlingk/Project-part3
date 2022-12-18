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
				<h3>change password</h3>
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
				<el-form-item v-if="passwordFlag" prop="password2">
					<el-row :gutter="24">
						<el-col :span="passwordFlag?24:19">
							<el-input v-model="signupForm.password2" prefix-icon="iconfont icon-password" :type="flag2?'text':'password'" @focus="focusPassword2()">
								<i slot="suffix" :class="flag2? 'iconfont icon-yanjing_yincang': 'iconfont icon-yanjing_xianshi'" @click="flag2=!flag2"/>
							</el-input>
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
				<a href="/login">login now!</a>
			</p>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		var checkPasswd = (rule, value, callback) => {
		if(value == this.signupForm.password){
			callback();
		}else{
			callback(new Error('password not the same at twice'));
		}
	};
		return {
			signupForm: {
				username: 'enter the username',
				password: 'enter the password',
				password2: 'enter the password again'
			},
			// Check rules for signup form data

			signupRules: {
				username: [
					{ required: true, message: 'enter the username', trigger: 'blur' },
            		{ min: 3, max: 25, message: 'length 3 to 25 characters', trigger: 'blur' }
				],
				password: [
					{ required: true, message: 'enter the password', trigger: 'blur' },
            		{ min: 3, max: 25, message: 'length 3 to 25 characters', trigger: 'blur' }
				],
				password2: [
					// { required: true, message: 'enter the e-mail', trigger: 'blur' },
					{required: true, validator: checkPasswd, trigger: 'blur'}
				],
			},
			flag: true,
			flag2: true,
			passwordFlag: false,
			resetPasswordFlag: false,
			signupFlag: false,
			signupbox_h: '200px',
		}
	},
	methods: {
		// submit
		submit(){
			this.$refs.signupFormRef.validate(async valid =>{
				if(!valid) return;
				let data;
				this.$http.post("resetPassword?token="+this.$route.query.token, JSON.stringify({
					username: this.signupForm.username,
					password: this.$md5(this.signupForm.password2),
				})).then(res => {
					data = res.data;
					if(data.msg == "success"){
						this.$message.success('change success');
						this.$router.push({path: "/login"});
					}else{
						this.$message.error('change failed:'+data.msg);
					}
					console.log(res.data.msg);
				}).catch(error => {
					console.log(error);
				});
			});
		},
		checkUsername(){
			if(this.signupForm.username == ""){
				return;
			}
			if(this.signupForm.username == "enter the username"){
				return;
			}
			let isIn;
			this.$http.get("isIn?username="+this.signupForm.username).then(res => {
				isIn = res.data;
				console.log(isIn);
				if(isIn){
					this.passwordFlag = true;
					this.signupbox_h = '300px';
					this.resetPasswordFlag = true;
				}else{
					this.signupFlag = true;
					this.$message.warning('username not exist');
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
		focusPassword2(){
			if(this.signupForm.password2 == 'enter the password again'){
				this.signupForm.password2 = "";
				this.flag2 = false;
			}
		},
	},
}
</script>

<style lang="less" scoped>
.signup_container{
	// background color
	background-color: #2b4b6b;
	height: 100%;
}

.logo{
	width: 180px;
	height: 130px;	
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
