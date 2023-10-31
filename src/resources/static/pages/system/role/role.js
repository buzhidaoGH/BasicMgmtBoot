layui.use(['layer', 'table', 'jquery'], function () {
  const $ = layui.$,
    layer = layui.layer,
    table = layui.table,
    loadId = utils.loadingFun()

  // 初始化表渲染
  table.render({
    elem: '#roleTable',
    defaultToolbar: ['filter'],
    skin: "self-class",
    cols: [[
      {type: 'checkbox', fixed: 'left'},
      {
        field: 'number', title: '序号', width: 60, unresize: true, fixed: 'left',
        align: 'center', templet: date => date.LAY_INDEX
      },
      {field: 'roleName', title: '角色名称', width: 100, unresize: true, align: 'center'},
      {field: 'roleKey', title: '权限字符', width: 155, unresize: true, align: 'center'},
      {
        field: 'status', title: '角色状态', width: 100, unresize: true,
        align: 'center',
        templet: ({status}) => `<div lay-event="changeStatus" style="width: 45px;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);" class="toggle-switch ${ status === '0' ? 'open' : '' }"><span class="switch"></span></div>`
      },
      {field: 'createBy', title: '创建人', width: 90, unresize: true, align: 'center'},
      {field: 'createTime', title: '创建时间', width: 177, unresize: true, align: 'center', sort: true},
      {field: 'updateBy', title: '更新人', width: 90, unresize: true, align: 'center'},
      {field: 'updateTime', title: '更新时间', width: 177, unresize: true, align: 'center', sort: true},
      {field: 'remark', title: '备注', unresize: true}
    ]],
    data: [],
    page: true,
    limit: 5, limits: [5, 10],
    height: '328'
  })

  getSysRoleList()
  layer.close(loadId)

  function getSysRoleList() {
    $.ajax({
        url: ctxUrl + '/sys_role/role_list',
        type: 'post', dataType: 'json', async: false
      })
      .done(({code, data}) => {
        if (code === 0) {
          table.reload('roleTable', {data: data})
        }
      })
      .fail(function () { window.location.reload()})
  }
})