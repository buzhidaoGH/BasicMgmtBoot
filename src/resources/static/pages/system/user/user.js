;(function () {
  layui.use(['form', 'layer', 'table', 'jquery'], function () {
    const form = layui.form,
      $ = layui.$,
      layer = layui.layer,
      table = layui.table,
      loadId = utils.loadingFun()

    // 自定义规则
    form.verify({
      phone_msg: function (value, item) {
        if (value !== "") {
          if (!/^1[3|4578]\d{9}$/.test(value)) {return '手机号格式不正确'}
        }
      }
    })

    // 表单提交事件
    form.on('submit(search-submit)', function (data) {
      let field = data.field // 获取表单全部字段值
      field.status === '2' ? field.status = undefined : ''
      field.userName === '' ? field.userName = undefined : ''
      field.nickName === '' ? field.nickName = undefined : ''
      field.phone === '' ? field.phone = undefined : ''
      getSysUserList(field)
      return false // 阻止默认 form 跳转
    })

    // 初始化表渲染
    table.render({
      elem: '#userTable',
      toolbar: '#toolbar',
      defaultToolbar: ['filter'],
      skin: "self-class",
      cols: [[
        {type: 'checkbox', fixed: 'left'},
        {
          field: 'number', title: '序号', width: 60, unresize: true, fixed: 'left',
          align: 'center', templet: date => date.LAY_INDEX
        },
        {field: 'userName', title: '账号', width: 100, unresize: true, align: 'center'},
        {field: 'nickName', title: '昵称', width: 100, unresize: true, align: 'center'},
        {
          field: 'gender', title: '性别', width: 70, unresize: true,
          align: 'center', templet: ({gender}) => gender === '1' ? '女' : gender === '0' ? '男' : '未知'
        },
        {field: 'phone', title: '联系方式', width: 140, unresize: true, align: 'center'},
        {
          field: 'avatar', title: '用户头像', width: 100, unresize: true,
          align: 'center', templet: ({avatar}) => `<img  src="${ ctxUrl + avatar }" alt="" width="28px"/>`
        },
        {
          field: 'status', title: '用户状态', width: 100, unresize: true,
          align: 'center',
          templet: ({status}) => `<div lay-event="changeStatus" style="width: 45px;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);" class="toggle-switch ${ status === '0' ? 'open' : '' }"><span class="switch"></span></div>`
        },
        {field: 'loginDate', title: '登陆时间', width: 177, unresize: true, align: 'center', sort: true},
        {field: 'remark', title: '备注', width: 90, unresize: true},
        {
          title: '操作', unresize: true, align: 'center',
          templet: `<div><a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit"><i class="fa fa-edit"></i> 编辑</a>
<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete"><i class="fa fa-trash-o"></i> 删除</a>
<a class="layui-btn layui-btn-xs layui-btn-warm" lay-event="details">查看详情页</a></div>`
        }
      ]],
      data: [],
      page: true,
      limit: 5, limits: [5, 10, 20, 50, 100],
      height: '328'
    })

    // 表格渲染成功后,请求一次
    getSysUserList()

    // 头部工具栏事件监听
    table.on('toolbar(userTable)', function ({event}) {
      switch (event) {
        case 'delete':
          const {data} = table.checkStatus('userTable')
          if ($.isEmptyObject(data)) {
            layer.msg('最少选择一项', {time: 1000})
            return
          }
          const nameList = data.map(({userName}) => userName),
            userIdList = data.map(({userId}) => userId)
          deleteSysUserByIds({nameList, userIdList})
          break
        case 'add':
          openNewPage(["添加用户", '/sys_user/user_add'])
          break
      }
    })

    // 表内每一格事件监听
    table.on('tool(userTable)', function ({data, event, tr}) {
      switch (event) {
        case 'edit':
          openNewPage(["修改用户", '/sys_user/user_edit/#/userId=' + data.userId])
          break
        case 'delete':
          deleteSysUserByIds({nameList: [data.userName], userIdList: [data.userId]})
          break
        case 'details':
          openNewPage(["用户详情", '/sys_user/user_details/#/userId=' + data.userId])
          break
        case 'changeStatus':
          const $curChange = tr.find("td[data-field='status'] div[lay-event='changeStatus']")
          let openFlag = $curChange.hasClass('open')
          layer.open({
            title: '系统提示',
            content: `确认要${ openFlag ? '停用' : '启用' }账号${ data.userName }吗？`,
            move: false, shadeClose: true, skin: 'self-class', icon: 3,
            btn: ["确认", "取消"],
            yes: function (index) {
              $.ajax({
                  url: ctxUrl + '/sys_user/change_user_status',
                  method: 'post',
                  dataType: "json",
                  contentType: "application/json",
                  data: JSON.stringify({userId: data.userId, status: openFlag ? '1' : '0'})
                })
                .done(({message}) => {
                  layer.msg(message, {time: 1000})
                  layer.close(index)
                  $curChange.toggleClass('open')
                })
                .fail(function () { window.location.reload()})
            }
          })
          break
      }
    })
    layer.close(loadId)
    /**
     * 获取用户数据集合,并更新表数据
     */
    function getSysUserList(field = undefined) {
      $.ajax({
          url: ctxUrl + '/sys_user/user_list',
          type: 'post', data: field,
          async: false,
          dataType: 'json'
        })
        .done(({code, data}) => {
          if (code === 0) {
            table.reload('userTable', {data: data})
          }
        })
        .fail(function () { window.location.reload()})
    }

    /**
     * 通过Ids删除用户
     */
    function deleteSysUserByIds({nameList, userIdList}) {
      layer.open({
        title: '系统提示',
        content: '是否删除账号：' + nameList.join(),
        move: false, shadeClose: true, skin: 'self-class',
        yes: function (index) {
          $.ajax({
              url: ctxUrl + '/sys_user/delete_user',
              method: 'post',
              dataType: "json",
              contentType: "application/json",
              data: JSON.stringify(userIdList)
            })
            .done(({message}) => {
              layer.msg(message, {time: 1000})
              layer.close(index)
              getSysUserList()
            })
            .fail(function () { window.location.reload()})
        }
      })
    }

    /**
     * 打开新页面
     */
    function openNewPage([title, url]) {
      layer.open({
        title: title, type: 2, shade: 0.2,
        maxmin: true, move: false, skin: 'self-class',
        shadeClose: true, area: ['90%', '90%'],
        content: ctxUrl + url
      })
    }
  })
})()