;(function () {
  layui.use(['form', 'jquery', 'layer'], function () {
    const form = layui.form
      , $ = layui.jquery
      , layer = layui.layer
      , nowParentId = parseInt(layui.router().search['parentId'])
      , loadId = utils.loadingFun()
    $.when($.ajax({url: ctxUrl + '/sys_dict_data/root_dict', method: 'post'})
      , $.ajax({url: ctxUrl + '/assets/lib/font-awesome/awesome_name.json'}))
      .done(function ([rootDict], [icoList]) {
        const $select = $("select[name='parentType']")
          , $icon = $("input[name='icon']")
          , $icoListDom = $(utils.generateIcoList(icoList))
        $select.append(`<option value="">无</option>`)
        for (let datum of rootDict.data) {
          let flagSelect = datum['dictId'] === nowParentId ? "selected" : ""
          $select
            .append(
              `<option value="${ datum['dictId'] }" ${ flagSelect }>${ datum['dictLabel'] }</option>`)
        }
        $icon.click(function (event) {
          event.preventDefault()
          $icoListDom.toggle()
        })
        $icoListDom.on('click', 'i', function (event) {
          if ($(this).is('i')) {
            $icon.attr("value", $(this).attr('class').split(' ').at(-1))
            $icoListDom.toggle()
          }
        })
        $icon.after($icoListDom)
        form.render('select', 'dictAddForm')
      })
      .fail(function () { window.location.reload()})
      .always(function(){layer.close(loadId)});
    form.on('submit(add-submit)', function ({field}) {
      field['parentId'] = field['parentType']
      delete field['parentType']
      $.ajax({url: ctxUrl + '/sys_dict_data/add_dict_data', method: 'post', data: field})
        .done(function ({code, message, data}) {
          if (code === 0) {
            parent.layer.msg(data, {icon: 1, time: 1000},
              function () {
                parent.layui.$("fieldset.layui-elem-field button.layui-btn").click()
                parent.layer.close(parent.layer.getFrameIndex(window.name))
              }
            )
          } else {
            parent.layer.msg(data, {icon: 2})
          }
        })
        .fail(function () { window.location.reload()})
      return false
    })
    $("#dictReply").on('click', () => { parent.layer.close(parent.layer.getFrameIndex(window.name))})
  })
})()