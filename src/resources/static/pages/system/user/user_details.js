;(function () {
  layui.use(['laytpl', 'jquery', 'layer'], function () {
    const $ = layui.$
      , laytpl = layui.laytpl
      , userId = layui.router().search.userId ??= '100'
      , $basicInfo = $("div.card-basic.basic-info")
      , $otherInfo = $("div.card-basic.other-info")
      , $tmplBasic = $("#templateBasic")
      , $tmplOther = $("#templateOther")
      , loadId = utils.loadingFun()

    $.ajax({
        url: ctxUrl + '/sys_user/user_list',
        type: 'post', data: {userId},
        dataType: 'json'
      })
      .done(function ({data}) {
        const tmplData = data[0]
        tmplData.gender = tmplData.gender === '0' ? '男' : '女'
        tmplData.status = tmplData.status === '0' ? '正常' : '停用'
        renderBasic(data[0], $tmplBasic, $basicInfo)
        renderBasic(data[0], $tmplOther, $otherInfo)
      })
      .fail(function () { window.location.reload()})
      .always(function () {layer.close(loadId)})

    function renderBasic(data, templateStr, targetView) {
      laytpl($(templateStr).html()).render(data, function (htmlStr) {$(targetView).html(htmlStr)})
    }
  })
})()