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
				<h3>find password</h3>
			</div>
			<!-- form field -->
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
				<el-form-item v-if="passwordFlag" prop="email">
					<el-row :gutter="24">
						<el-col :span="passwordFlag?24:19">
							<el-input v-model="signupForm.email" prefix-icon="el-icon-s-promotion" type="text" @focus="focusEmail()">
								<!-- <i slot="suffix" :class="flag? 'iconfont icon-yanjing_yincang': 'iconfont icon-yanjing_xianshi'" @click="flag=!flag"/> -->
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
	</div>
</template>

<script>
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
			signupForm: {
				username: 'input username',
				email: 'input email'
			},
			// signup Form Data Verification Rules

			signupRules: {
				username: [
					{ required: true, message: 'enter the username', trigger: 'blur' },
            		{ min: 3, max: 25, message: '3 to 25 characters in length', trigger: 'blur' }
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
		// submit
		submit(){
			this.$refs.signupFormRef.validate(async valid =>{
				if(!valid) return;
				let data;
				this.$http.post("foundPassword", JSON.stringify({
					username: this.signupForm.username,
					email: this.signupForm.email,
				})).then(res => {
					data = res.data;
					if(data.msg == "success"){
						this.$message.success('success');
						this.$router.push({path: "/login"});
					}else if(data.msg == "user not exist"){
						this.$message.error('failed:'+data.msg);
						this.$router.push({path: "/signup"});
					}else if(data.msg == "more than 5 times"){
						this.$message.error('failed: Exceeds the limit of times');
					}
					else{
						this.$message.error('failed:'+data.msg);
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
					this.$message.warning('user not exist');
				}
			}).catch(error => {
				console.log(error);
			});
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

	},
}
</script>

<style lang="less" scoped>
// Root Node Style
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
		top: 65%;
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