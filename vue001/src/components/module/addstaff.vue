<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/Main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>员工操作</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
          <div class="oo">所有员工</div>
        </el-col>
        <el-col :span="8">
          <el-button type ="primary" @click="addDialogVisible = true">加入</el-button>
        </el-col>
      </el-row>
      <el-table :data="staffList" style="width: 100%">
        <el-table-column prop="staff_id" label="职工号" width="180"> </el-table-column>
        <el-table-column prop="name" label="姓名" width="180"> </el-table-column>
        <el-table-column prop="title" label="职位" width="180"> </el-table-column>
        <el-table-column prop="login_code" label="密码" width="180"> </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope" >
            <el-button type="primary" @click="showeditdialog(scope.row)" icon="el-icon-edit" size="mini" > </el-button>
            <el-button type="danger"  @click="deletestaff(scope.row)" icon="el-icon-delete" size="mini" > </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog title="添加职员" :visible.sync="addDialogVisible" width="50%" @close='DialogClosed'>
      <el-form ref="addformref" :model="addform" >
        <el-form-item label="职工号" prop = "staff_id">
          <el-input v-model="addform.staff_id" clearable></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop = "name">
          <el-input v-model="addform.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="职位" prop = "title">
          <el-input v-model="addform.title" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop = "login_code">
          <el-input v-model="addform.login_code" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" >
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addstaff">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改职员" :visible.sync="editDialogVisible" width="50%" @close='editDialogClosed'>
      <el-form ref="editformref" :model="editform" >
        <el-form-item label="职工号" prop = "staff_id">
          <el-input v-model="editform.staff_id" clearable disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop = "name">
          <el-input v-model="editform.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="职位" prop = "title">
          <el-input v-model="editform.title" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop = "login_code">
          <el-input v-model="editform.login_code" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" >
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editstaff">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      staffList: [],
      total: 0,
      addDialogVisible: false,
      addform: {
        staff_id: '',
        name: '',
        title: '',
        login_code: ''
      },
      editDialogVisible: false,
      editform: {
        hotel_id: '',
        name: '',
        title: '',
        login_code: ''
      }
    }
  },
  created () {
    this.getstaffList()
  },
  methods: {
    async getstaffList () {
      const result = await this.$http.post('getstaffop', {'token': window.sessionStorage.getItem('token')})
      const res = result.data
      if (res.code === 0) {
        return this.$message.error('获取信息失败,请重新登陆')
      }
      this.staffList = res.stafflist
      console.log(res.stafflist)
      this.total = result.total
    },
    DialogClosed () {
      this.$refs.addformref.resetFields()
    },
    async addstaff () {
      const result = await this.$http.post('addstaff', {'token': window.sessionStorage.getItem('token'), 'staffmsg': this.addform})
      if (result.data.code === 0) {
        return this.$message.error('加入失败，请重试')
      }
      this.$message.success('加入成功')
      this.$refs.addformref.resetFields()
      this.addDialogVisible = false
      this.getstaffList()
    },
    showeditdialog (editmsg) {
      this.editform = editmsg
      this.editDialogVisible = true
    },
    editDialogClosed () {
      this.$refs.editformref.resetFields()
    },
    async editstaff () {
      const result = await this.$http.post('editstaff', {'token': window.sessionStorage.getItem('token'), 'staffmsg': this.editform})
      if (result.data.code === 0) {
        return this.$message.error('修改失败，请重试')
      }
      this.$message.success('修改成功')
      this.$refs.editformref.resetFields()
      this.editDialogVisible = false
      this.getstaffList()
    },
    async deletestaff (deletemsg) {
      const result = await this.$http.post('deletestaff', {'token': window.sessionStorage.getItem('token'), 'hotelmsg': deletemsg})
      if (result.data.code === 0) {
        return this.$message.error('删除失败，请重试')
      }
      this.$message.success('删除成功')
      this.gethotelList()
    }
  }
}

</script>

<style scoped>
  .el-breadcrumb {
      margin-bottom: 15px;
      font-size: 12px;
  }
  .el-card {
      box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
  }
  .oo {
      font-size: 25px;
      text-align: left;
  }
  .el-table {
      margin-top: 15px;
  }
</style>
