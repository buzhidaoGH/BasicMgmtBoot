;(function () {
  const url = ctxUrl + "/sys_user/login",
    toUrl = ctxUrl + "/index",
    captchaUrl = ctxUrl + "/captcha"
  layui.use(['form', 'jquery', 'layer'], function () {
    const $ = layui.jquery,
      form = layui.form,
      layer = layui.layer
    let loadId = utils.loadingFun()
    $('.bind-password').on('click', function () {
      if ($(this).hasClass('icon-5')) {
        $(this).removeClass('icon-5')
        $("input[name='password']").attr('type', 'password')
      } else {
        $(this).addClass('icon-5')
        $("input[name='password']").attr('type', 'text')
      }
    })

    $('.icon-nocheck').on('click', function () {
      if ($(this).hasClass('icon-check')) {
        $(this).removeClass('icon-check')
      } else {
        $(this).addClass('icon-check')
      }
    })

    $('#refreshCaptcha').on('click', function () {
      $(this).attr('src', captchaUrl + "?time=" + new Date().getTime())
    })
    // 进行登录操作
    form.on('submit(login)', function (data) {
      const subBtn = $(".login-bottom .login-btn")
      data = data.field
      subBtn.attr("disabled", "disabled")
        .addClass("layui-btn-disabled")
      $.post(url, data, (res) => {
        if (res.code === 0) {
          layer.msg(res.message, {icon: 6, time: 1000}, () => {
            let frameIndex = parent.layer.getFrameIndex(window.name)
            if (frameIndex) { parent.layer.close(frameIndex)} else {window.location = toUrl}
          })
        } else {
          layer.msg(res.message, {icon: 5, anim: 6})
          subBtn.removeAttr("disabled").removeClass("layui-btn-disabled")
          $('#refreshCaptcha').attr('src', captchaUrl + "?time=" + new Date().getTime())
        }
      })
      return false
    })
    layer.close(loadId)
  })
})()