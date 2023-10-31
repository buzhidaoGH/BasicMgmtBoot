layui.use(['layer', 'table', 'jquery'], function () {
  const $ = layui.$,
    layer = layui.layer,
    table = layui.table,
    loadId = utils.loadingFun()
  table.render({
    elem: '#userTable',
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
        field: 'gender', title: '性别', width: 65, unresize: true,
        align: 'center', templet: ({gender}) => gender === '1' ? '女' : gender === '0' ? '男' : '未知'
      },
      {field: 'phone', title: '联系方式', width: 130, unresize: true, align: 'center'},
      {
        field: 'avatar', title: '用户头像', width: 100, unresize: true,
        align: 'center', templet: ({avatar}) => `<img  src="${ ctxUrl + avatar }" alt="" width="28px"/>`
      },
      {field: 'loginIp', title: '登陆主机', width: 130, unresize: true, align: 'center', sort: true},
      {field: 'browser', title: '浏览器', width: 110, unresize: true, align: 'center', sort: true},
      {field: 'system', title: '操作系统', width: 110, unresize: true, align: 'center', sort: true},
      {field: 'loginDate', title: '登陆时间', unresize: true, align: 'center', sort: true}
    ]],
    data: [], page: true,
    limit: 5, limits: [5, 10, 20, 50],
    height: '328'
  })
  getOnlineSysUserList()
  setInterval(function () {
    getOnlineSysUserList()
  }, 60 * 5 * 1000)
  layer.close(loadId)
  function getOnlineSysUserList() {
    $.ajax({
        url: ctxUrl + '/sys_user/user_online',
        type: 'post', async: false, dataType: 'json'
      })
      .done(({code, data}) => {
        if (code === 0) {
          table.reload('userTable', {data: data})
        }
      })
      .fail(function () { window.location.reload()})
  }
})