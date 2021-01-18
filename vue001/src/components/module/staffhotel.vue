<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/Main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>酒店员工查询</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
          <el-input placeholder="请使用酒店编号查询" v-model="hotel_id" clearable></el-input>
        </el-col>
        <el-col :span="4">
            <el-button @click="getstaffList" type ="primary">查询</el-button>
        </el-col>
        <el-col :span="4">
            <el-button @click="addDialogVisible = true" type ="primary">加入</el-button>
        </el-col>
      </el-row>
      <el-table :data="staffList" style="width: 100%">
        <el-table-column prop="staff_id" label="职工号" width="180"> </el-table-column>
        <el-table-column prop="name" label="姓名" width="180"> </el-table-column>
        <el-table-column prop="title" label="职位" width="180"> </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button type="primary" @click="addDialogVisible = true" icon="el-icon-circle-plus" size="mini" > </el-button>
            <el-button type="danger"  @click="deletestaff(scope.row)" icon="el-icon-delete" size="mini" > </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog title="添加员工" :visible.sync="addDialogVisible" width="50%" @close='DialogClosed'>
        <el-form ref="addformref" :model="addform" >
          <el-form-item label="职工号" prop = "staff_id">
            <el-input placeholder="请输入加入职工号" v-model="addform.staff_id" clearable></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" >
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addstaffhotel">确 定</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      staffList: [],
      addDialogVisible: false,
      hotel_id: '',
      addform: {staff_id: ''}
    }
  },
  methods: {
    async getstaffList () {
      const result = await this.$http.post('getstaffhotel', {'token': window.sessionStorage.getItem('token'), 'hotel_id': this.hotel_id})
      const res = result.data
      if (res.code === 0) {
        return this.$message.error('获取信息失败请重新输入')
      }
      this.staffList = res.stafflist
    },
    DialogClosed () {
      this.$refs.addformref.resetFields()
    },
    async addstaffhotel () {
      const result = await this.$http.post('addstaffhotel', {'token': window.sessionStorage.getItem('token'), 'hotel_id': this.hotel_id, 'staff_id': this.addform.staff_id})
      if (result.data.code === 0) {
        return this.$message.error('加入失败，请重试')
      }
      this.$message.success('加入成功')
      this.$refs.addformref.resetFields()
      this.addDialogVisible = false
      this.gethotelList()
    },
    async deletestaff (staffmsg) {
      const result = await this.$http.post('deletestaffhotel', {'token': window.sessionStorage.getItem('token'), 'hotel_id': this.hotel_id, 'staff_id': staffmsg.staff_id})
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
