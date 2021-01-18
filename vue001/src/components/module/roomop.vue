<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/Main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>酒店操作</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
          <div class="oo">酒店的房间状况</div>
        </el-col>
        <el-col :span="8">
          <el-button type ="primary" @click="addDialogVisible = true">加入</el-button>
        </el-col>
      </el-row>
      <el-table :data="hotelList" style="width: 100%">
        <el-table-column prop="hotel_id" label="酒店号" width="180"> </el-table-column>
        <el-table-column prop="room_type" label="房型" width="180"> </el-table-column>
        <el-table-column prop="room_price" label="价格" width="180"> </el-table-column>
        <el-table-column prop="total" label="总房间数" width="180"> </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope" >
            <el-button type="primary" @click="showeditdialog(scope.row)" icon="el-icon-edit" size="mini" > </el-button>
            <el-button type="danger"  @click="deleteroom(scope.row)" icon="el-icon-delete" size="mini" > </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog title="添加房间" :visible.sync="addDialogVisible" width="50%" @close='DialogClosed'>
      <el-form ref="addformref" :model="addform" >
        <el-form-item label="酒店号" prop = "hotel_id">
          <el-input v-model="addform.hotel_id" clearable></el-input>
        </el-form-item>
        <el-form-item label="房型" prop = "room_type">
          <el-input v-model="addform.room_type" clearable></el-input>
        </el-form-item>
        <el-form-item label="房间价格" prop = "room_price">
          <el-input v-model="addform.room_price" clearable></el-input>
        </el-form-item>
        <el-form-item label="房间数量" prop = "total">
          <el-input v-model="addform.total" clearable></el-input>
        </el-form-item>
    </el-form>
      <div slot="footer" >
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addroom">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改房间" :visible.sync="editDialogVisible" width="50%" @close='editDialogClosed'>
      <el-form ref="editformref" :model="editform" >
        <el-form-item label="酒店号" prop = "hotel_id">
          <el-input v-model="editform.hotel_id" clearable disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="房型" prop = "room_type">
          <el-input v-model="editform.room_type" clearable disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="房间价格" prop = "room_price">
          <el-input v-model="editform.room_price" clearable></el-input>
        </el-form-item>
        <el-form-item label="房间数量" prop = "total">
          <el-input v-model="editform.total" clearable></el-input>
        </el-form-item>
    </el-form>
      <div slot="footer" >
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editroom">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      hotelList: [],
      total: 0,
      addDialogVisible: false,
      addform: {
        hotel_id: '',
        room_type: '',
        room_price: '',
        total: ''
      },
      editDialogVisible: false,
      editform: {
        hotel_id: '',
        room_type: '',
        room_price: '',
        total: ''
      }
    }
  },
  created () {
    this.gethotelList()
  },
  methods: {
    async gethotelList () {
      const result = await this.$http.post('gethotelop', {'token': window.sessionStorage.getItem('token')})
      const res = result.data
      if (res.code === 0) {
        return this.$message.error('获取信息失败,请重新登陆')
      }
      this.hotelList = res.hotellist
      this.total = result.total
    },
    DialogClosed () {
      this.$refs.addformref.resetFields()
      console.log(this.addform)
    },
    async addroom () {
      const result = await this.$http.post('addroom', {'token': window.sessionStorage.getItem('token'), 'roommsg': this.addform})
      if (result.data.code === 0) {
        return this.$message.error('加入失败，请重试')
      }
      this.$message.success('加入成功')
      this.$refs.addformref.resetFields()
      this.addDialogVisible = false
      this.gethotelList()
    },
    showeditdialog (editmsg) {
      this.editform = editmsg
      this.editDialogVisible = true
    },
    editDialogClosed () {
      this.$refs.editformref.resetFields()
    },
    async editroom () {
      const result = await this.$http.post('editroom', {'token': window.sessionStorage.getItem('token'), 'roommsg': this.editform})
      if (result.data.code === 0) {
        return this.$message.error('修改失败，请重试')
      }
      this.$message.success('修改成功')
      this.$refs.editformref.resetFields()
      this.editDialogVisible = false
      this.gethotelList()
    },
    async deleteroom (deletemsg) {
      const result = await this.$http.post('deleteroom', {'token': window.sessionStorage.getItem('token'), 'roommsg': deletemsg})
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
