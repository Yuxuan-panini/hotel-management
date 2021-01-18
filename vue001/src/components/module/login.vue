<template>
  <div class="login">
    <img src="../../assets/logo.png">
    <el-form ref = "loginformref" :model="loginform"  label-width="80px">
      <el-form-item prop = "name">
        <el-input placeholder="请输入用户名" v-model="loginform.name" clearable class="input_style"></el-input>
      </el-form-item>
      <el-form-item prop = "pwd">
        <el-input placeholder="请输入密码" v-model="loginform.pwd" type = "password" clearable class="input_style"></el-input>
      </el-form-item>
      <el-form-item >
        <el-button type="primary" @click="loginop" class="login_style">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      loginform: {
        name: '',
        pwd: ''
      }
    }
  },
  methods: {
    async loginop () {
      const result = await this.$http.post('login', this.loginform)
      console.log(result.data)
      if (result.data.code === 0) {
        this.$refs.loginformref.resetFields()
        return this.$message.error('登陆失败')
      }
      this.$message.success('登陆成功')
      console.log(result)
      window.sessionStorage.setItem('token', result.data['token'])
      console.log(result.data)
      window.sessionStorage.setItem('permission', result.data['permission'])
      this.$refs.loginformref.resetFields()
      this.$router.push({name: 'Main'})
    }
  }
}
</script>

<style scoped>
  .login{
    margin-top: 200px;
  }
  .input_style{
    width: 200px;
    right: 40px;
    margin-bottom: 10px;
  }
  .login_style{
    position: relative;
    width: 200px;
    right: 40px;
  }
</style>
