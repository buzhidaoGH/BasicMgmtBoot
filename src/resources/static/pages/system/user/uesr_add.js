;(function () {
  layui.use(['form', 'jquery', 'layer'], function () {
    const form = layui.form,
      $ = layui.$, layer = layui.layer,
      $passwordIcon = $(".password-icon"),
      $fileUpload = $(".file-upload"),
      transferFile = document.createElement('input'),
      loadId = utils.loadingFun()
    transferFile.type = 'file'
    transferFile.accept = 'image/*'
    // 渲染form表单的select
    form.render('select', 'userAddForm')
    // submit按钮提交表单信息
    form.on('submit(search-submit)', function ({field}) {
      field.status ? (field.status = '1') : ''
      // 处理清除空field段
      field = Object.fromEntries(Object.entries(field).filter(([_, value]) => value))
      const formData = new FormData()
      for (let key of Object.keys(field)) {formData.append(key, field[key])}
      if (transferFile.value) {formData.append('avatarFile', transferFile.files[0])}
      $.ajax({
          url: ctxUrl + '/sys_user/add_sys_user',
          method: 'post', data: formData,
          processData: false,// 不转换数据
          contentType: false,// 不转换类型(保留form单数据类型)
          dataType: "json"
        })
        .done(function ({code, message, data}) {
          if (code === 0) {
            parent.layer.msg(
              "用户创建成功！",
              {icon: 1, time: 1000},
              function () {
                parent.layui.$("button.layui-btn.search-submit").click()
                parent.layer.close(parent.layer.getFrameIndex(window.name))
              }
            )
          } else {
            parent.layer.msg("用户创建失败", {icon: 2})
          }
        })
        .fail(function () { window.location.reload()})
      return false
    })
    // 删除弹窗返回主页面
    $("#userAddReply").on('click', () => { parent.layer.close(parent.layer.getFrameIndex(window.name))})
    // 按下密码栏eye变为text
    $passwordIcon.on('mousedown', function () {$(this).prev().attr("type", "text")})
    // 松开密码栏eye变为password
    $passwordIcon.on('mouseup', function () {$(this).prev().attr("type", "password")})
    // 点击文件上传fileUpload
    $fileUpload.on('click', function (event) {
      event.preventDefault()
      transferFile.value = '' // 清空文件
      fileNameShow()
      transferFile.onchange = fileNameShow
      transferFile.click()
    })
    // 获取role角色列表
    $.ajax({
        url: ctxUrl + '/sys_role/role_list',
        method: 'post',
        dataType: "json"
      })
      .done(({data}) => {
        const $roleSelect = $('.roleSelect')
        for (let i = 0; i < data.length; i++) {
          $roleSelect.append(
            `<option value="${ data[i]['roleId'] }" ${ i === 0 ? 'selected' : '' }>${ data[i]['roleName'] }</option>`)
        }
        form.render('select', 'userAddForm')
      })
      .fail(function () { window.location.reload()})
      .always(function () {layer.close(loadId)})
    // 文件名称的展示
    function fileNameShow() {$fileUpload.next().text(transferFile.value ? transferFile.files[0]?.name : '')}
  })
})()