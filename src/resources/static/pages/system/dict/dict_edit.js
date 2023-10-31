;(function () {
  layui.use(['form', 'jquery', 'layer'], function () {
    const form = layui.form
      , $ = layui.jquery
      , layer = layui.layer
      , loadId = utils.loadingFun()
      , router = new URLSearchParams(location.href)
      , nowParentId = parseInt(router.get('parentId'))
      , nowDictId = parseInt(router.get('dictId'))
    $.when($.ajax({url: ctxUrl + '/sys_dict_data/root_dict', method: 'post'}),
      $.ajax({url: ctxUrl + '/sys_dict_data/dict_data', method: 'post', data: {dictId: nowDictId}}),
      $.ajax({url: ctxUrl + '/assets/lib/font-awesome/awesome_name.json'})
      )
      .done(function ([rootDict], [dictData], [icoList]) {
        const $select = $("select[name='parentType']")
          , $dictId = $("input[name='dictId']")
          , $dictLabel = $("input[name='dictLabel']")
          , $dictType = $("input[name='dictType']")
          , $dictSort = $("input[name='dictSort']")
          , $dictValue = $("input[name='dictValue']")
          , $icon = $("input[name='icon']")
          , $remark = $("input[name='remark']")
          , $icoListDom = $(utils.generateIcoList(icoList))
        $select.append(`<option value="">无</option>`)
        for (let datum of rootDict.data) {
          let flagSelect = datum['dictId'] === nowParentId ? "selected" : ""
          $select
            .append(
              `<option value="${ datum['dictId'] }" ${ flagSelect }>${ datum['dictLabel'] }</option>`)
        }
        $dictId.attr("value", dictData['data']['dictId'])
        $dictLabel.attr("value", dictData['data']['dictLabel'])
        $dictType.attr("value", dictData['data']['dictType'])
        $dictSort.attr("value", dictData['data']['dictSort'])
        $dictValue.attr("value", dictData['data']['dictValue'])
        $icon.attr("value", dictData['data']['icon'])
        $remark.attr("value", dictData['data']['remark'])
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
        form.render('select', 'dictEditForm')
      })
      .fail(function () { window.location.reload()})
      .always(function(){layer.close(loadId)});
    form.on('submit(update-submit)', function ({field}) {
      field['parentId'] = field['parentType']
      delete field['parentType']
      $.ajax({url: ctxUrl + '/sys_dict_data/update_dict_data', method: 'post', data: field})
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
      return false
    })
    $("#dictReply").on('click', () => { parent.layer.close(parent.layer.getFrameIndex(window.name))})
  })
})()