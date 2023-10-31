;(function () {
  layui.use(['laytpl', 'jquery', 'form', 'layer'], function () {
    const $ = layui.$
      , laytpl = layui.laytpl
      , form = layui.form
      , layer = layui.layer
      , userId = layui.router().search.userId ??= '100'
      , $basicInfo = $("div.card-basic.basic-info")
      , $tmplBasic = $("#templateBasic")
      , infoRoot = {userInfo: null, roleInfo: null}
      , transferFile = document.createElement('input')
      , formFlag = {rawData: null, change: false}// 判断表单是否修改
      , loadId = utils.loadingFun()

    transferFile.type = 'file'
    transferFile.accept = 'image/*'

    // 获取json数据,渲染模板
    $.when($.ajax({url: ctxUrl + '/sys_user/user_list', type: 'post', data: {userId}, dataType: 'json'})
      , $.ajax({url: ctxUrl + '/sys_role/role_list', method: 'post', dataType: "json"}))
      .done(function ([{data: [userInfo]}], [{data: roleInfo}]) {
        infoRoot.userInfo = userInfo
        infoRoot.roleInfo = roleInfo
        renderBasic({userInfo, roleInfo}, $tmplBasic, $basicInfo)
        form.render('select', 'userAddForm')
        // 给头像注册事件
        $(".basic-avatar .file-upload").on("click", function (event) {
          event.preventDefault()
          transferFile.value = '' // 清空文件
          fileNameShow()
          transferFile.onchange = fileNameShow
          transferFile.click()
        })
        // 注册一个更新随机密码事件
        $(".reset-password>button").on("click", resetPassword)
        formFlag.rawData = JSON.stringify($("#userAddForm").serializeArray())
        layer.close(loadId)
      })
      .fail(function () { window.location.reload()})
    // 删除弹窗返回主页面
    $("#userAddReply").on('click', () => { parent.layer.close(parent.layer.getFrameIndex(window.name))})
    // submit按钮提交表单信息
    form.on('submit(update-submit)', function ({field}) {
      if (formFlag.rawData !== JSON.stringify($("#userAddForm").serializeArray()) ||
        formFlag.change) {
        // 处理清除空field段
        field = Object.fromEntries(Object.entries(field).filter(([_, value]) => value))
        const formData = new FormData()
        for (let key of Object.keys(field)) {formData.append(key, field[key])}
        if (transferFile.value) {formData.append('avatarFile', transferFile.files[0])}
        $.ajax({
            url: ctxUrl + '/sys_user/update_sys_user',
            method: 'post', data: formData,
            processData: false,// 不转换数据
            contentType: false,// 不转换类型(保留form单数据类型)
            dataType: "json"
          })
          .done(function ({code, message}) {
            if (code === 0) {
              parent.layer.msg(message
                , {icon: 1, time: 1000}
                , function () {
                  parent.layui.$("button.layui-btn.search-submit").click()
                  parent.layer.close(parent.layer.getFrameIndex(window.name))
                }
              )
            } else {
              parent.layer.msg(message, {icon: 2})
            }
          })
          .fail(function () { window.location.reload()})
      }
      return false
    })
    // 函数 随机密码重置
    function resetPassword(event) {
      $.ajax({url: ctxUrl + '/sys_user/random_password', method: 'post', dataType: "json", data: {userId}})
        .done(function ({code, message, data}) {
          if (code === 0) {
            layer.open({
              type: 1, title: "新密码"
              , shadeClose: true
              ,
              content: `<div style="text-align: center;font-size: 20px;">${ utils.html2Escape(data) }</div>`
              , btn: ['复制', '关闭']
              , yes: function () {
                utils.copyText(data)
                  .then(function () {parent.layer.msg("新密码复制成功！", {icon: 1, time: 1000})})
                return false
              }
            })
          } else {
            layer.msg(message, {icon: 2, time: 1000})
          }
        })
        .fail(function () { window.location.reload()})
    }
    // 函数 基础内容模板渲染
    function renderBasic(data, templateStr, targetView) {
      laytpl($(templateStr).html()).render(data, function (htmlStr) {$(targetView).html(htmlStr)})
    }
    // 函数 avatar文件预览的展示
    function fileNameShow() {
      const $avatar = $(".basic-avatar .file-upload")
      if (transferFile.value && transferFile.files[0]) {
        // 方式1
        /*const readFile = new FileReader() // 创建FileReader对像;
        readFile.readAsDataURL(transferFile.files[0]) // 调用readAsDataURL方法读取文件;变为base64
        readFile.onload = function () {
          $avatar.attr("src", readFile.result)
        }*/
        // 方式2
        let objectURL = window.URL.createObjectURL(transferFile.files[0])
        formFlag.change = true
        $avatar.attr("src", objectURL)
      } else {
        $avatar.attr("src", infoRoot.userInfo['avatar'])
      }
    }
  })
})()