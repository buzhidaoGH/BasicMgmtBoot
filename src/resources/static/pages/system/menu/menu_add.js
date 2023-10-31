;(function () {
  layui.use(['jquery', 'layer', 'form'], function () {
    const layer = layui.layer
      , loadId = utils.loadingFun()
      , form = layui.form
      , $ = layui.jquery
      , router = new URLSearchParams(location.href)
      , nowParentId = router.get('parentId') ? router.get('parentId') : '0'
      , nowParentName = router.get('parentName') ? router.get('parentName') : '无'

    $.ajax({url: ctxUrl + '/assets/lib/font-awesome/awesome_name.json'})
      .done(function (icoList) {
        const $parentId = $('input[name="parentId"]')
          , $icon = $("input[name='icon']")
          , $icoList = utils.generateIcoListPro(icoList, callBack)
          , $menuBox = $(".form-group .menu-type-radio")
          , $formRadioChange = $('form .radio-change')
        $parentId.attr('value', nowParentName)
        $parentId.attr('data-value', nowParentId)
        $parentId.click(function () {
          layer.open({
            title: "菜单选择", type: 2, shade: 0.2,
            maxmin: true, move: false, skin: 'self-class',
            shadeClose: true, area: ['380px', '500px'],
            content: ctxUrl + '/sys_menu/menu_tree/#/',
            btn: ['确认'],
            success(_, index) {
              const body = layer.getChildFrame('body', index)//建立父子联系【核心语句】
                , sonInput = body.find('input[name="menuId"]')
              sonInput.attr("value", $parentId.attr("value"))
              sonInput.attr("data-value", $parentId.attr("data-value"))
            },
            yes(index, _) {
              const body = layer.getChildFrame('body', index)//建立父子联系【核心语句】
                , sonInput = body.find('input[name="menuId"]')
              $parentId.attr("value", sonInput.attr("value"))
              $parentId.attr("data-value", sonInput.attr("data-value"))
              layer.close(index)
            }
          })
        })
        $icon.after($icoList.dom)
        $icon.click($icoList.toggle)
        $menuBox.append('<input type="radio" name="menuType" value="M" checked title="目录" />')
        $menuBox.append('<input type="radio" name="menuType" value="C" title="菜单" />')
        form.render('radio', 'menuAddForm')
        form.on('radio', function ({value}) {
          radioChange(value, $formRadioChange)
        })
        form.on('submit(add-submit)', function ({field}) {
          field['parentId'] = $parentId.attr('data-value')
          field['path'] ??= '#'
          field['isFrame'] ??= '1'
          field['visible'] ??= '1'
          $.ajax({url: ctxUrl + '/sys_menu/add_menu', method: 'post', data: field})
            .done(({code, data, message}) => {
              layer.msg(data, {time: 1000}, function () {
                parent.layer.close(parent.layer.getFrameIndex(window.name))
                parent.location.reload()
              })
            })
          return false
        })
        function callBack(name) {$icon.attr("value", name)}
      })
      .always(function () {layer.close(loadId)})
    $("#dictReply").on('click', () => { parent.layer.close(parent.layer.getFrameIndex(window.name))})
    /**
     * 单选切换事件函数
     */
    function radioChange(type, $dom) {
      $dom.html('')
      if ('C' === type) {
        $dom.append(`<div class="form-group"><label class="layui-col-md3 label_right required">请求地址：</label>
        <div class="layui-col-md7"><input type="text" class="layui-input" value="#" lay-verify="required" name="path" placeholder="输入项目路径或者合法地址"></div></div>`)
        $dom.append(`<div class="form-group"><label class="layui-col-md3 label_right required">打开方式：</label>
        <div class="layui-col-md7"><select name="isFrame"><option value="0">新窗口</option><option value="1" selected>内页签</option></select></div></div>`)
      }
      form.render(null, 'menuAddForm')
    }
  })
})()