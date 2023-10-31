window.utils = {
  /**简单获取uuid*/
  getUuid() {
    return 'xxxxxxxx'.replace(/[xy]/g, function (c) {
      const r = Math.random() * 16 | 0
      const v = c === 'x' ? r : (r & 0x3 | 0x8)
      return v.toString(16)
    })
  },
  /**menu排序*/
  arrayMenuSort({list, desc = false, sortName = "orderNum", childName = "children"}) {
    list.sort((item1, item2) => {
      if (item1[childName] && item1[childName].length !== 0) {
        this.arrayMenuSort({list: item1[childName], desc, sortName, childName})
      }
      if (desc) {
        return
      }
      return desc ? (item2[sortName] - item1[sortName])
        : (item1[sortName] - item2[sortName])
    })
  },
  /**menu数据转为HTML*/
  getSideHtml(dataList, isRoot = true) {
    const result = {code: -1, message: "传入数组为空！", data: ""}
    if (!(dataList && dataList.length !== 0)) return result
    result.code = 1
    result.message = "成功渲染侧边栏"
    if (isRoot) {
      for (let i = 0; i < dataList.length; i++) {
        const {menuId, menuName, path, isFrame, icon, children} = dataList[i]
        let flag = (children && children.length > 0)
        result.data += `<li class="layui-nav-item">`
        result.data +=
          `<a data-id="${ menuId }" data-path="${ path }" data-target="${ isFrame }" href="javascript:" ${ flag ? '' : 'class="side-site-active"' }>
<i class="fa ${ icon }"></i> <span>${ menuName }</span></a>`
        if (flag) {
          const resHtml = this.getSideHtml(children, false)
          result.data += resHtml.data
        }
        result.data += `</li>`
      }
    } else {
      for (let i = 0; i < dataList.length; i++) {
        const {menuId, menuName, path, isFrame, icon, children} = dataList[i]
        let flag = (children && children.length > 0)
        result.data += `<dl class="layui-nav-child">`
        result.data += `<dd>`
        result.data +=
          `<a data-id="${ menuId }" data-path="${ path }" data-target="${ isFrame }" href="javascript:" ${ flag ? '' : 'class="side-site-active"' }><i class="fa ${ icon }"></i> <span>${ menuName }</span></a>`
        if (flag) {
          const resHtml = this.getSideHtml(children, false)
          result.data += resHtml.data
        }
        result.data += `</dd>`
        result.data += `</dl>`
      }
    }
    return result
  },

  /**menu数据转为HTML*/
  getSideHtml2(data, isRoot = false) {
    const result = {code: -1, message: "传入数组为空！", data: ""}
    if (isRoot) {
      for (let i = 0; i < data.length; i++) {
        const {menuId, menuName, path, isFrame, icon, children, menuType} = data[i]
        let flag = children !== null && children.length > 0
        result.data += `<dd class="layui-nav-item">`
        result.data += `<a data-id="${ menuId }" data-type="${ menuType }" data-path="${ path }" data-target="${ isFrame }" href="javascript:" ${ flag ? '' : 'class="side-site-active"' } >
<i class="fa ${ icon }"></i> <span>${ menuName }</span></a>`
        result.data += this.getSideHtml2(data[i]).data
        result.data += `</dd>`
      }
    } else {
      if (data === null) {return result}
      if (data["children"] != null && data["children"].length > 0) {
        result.data += '<dl class="layui-nav-child">'
      }
      if (data["children"] != null && data["children"].length > 0) {
        for (let item of data["children"]) {
          const {menuId, menuName, path, isFrame, icon, menuType} = item
          let flag = item.children !== null && item.children.length > 0
          flag ? result.data += '<dd class="layui-nav-item">' : result.data += '<dd>'
          result.data += `<a data-id="${ menuId }" data-type="${ menuType }" data-path="${ path }" data-target="${ isFrame }" href="javascript:" ${ flag ? '' : 'class="side-site-active"' }>
<i class="fa ${ icon }"></i> <span>${ menuName }</span></a>`
          if (item.children == null) return result
          result.data += this.getSideHtml2(item).data
          result.data += '</dd>'
        }
        result.data += '</dl>'
      }
      return result
    }
    return result
  },
  /**打开外部新页面*/
  openNewWindow(url) {
    const tempA = document.createElement("a")
    tempA.setAttribute("target", "_blank")
    tempA.setAttribute("href", url)
    tempA.click()
    tempA.remove()
  },
  /**通用复制文本*/
  copyText(text) {
    if (navigator.clipboard && window.isSecureContext) {
      return navigator.clipboard.writeText(text)
    } else {
      const textArea = document.createElement('textarea')
      textArea.value = text
      document.body.appendChild(textArea)
      textArea.focus()
      textArea.select()
      return new Promise((res, rej) => {
        document.execCommand('copy') ? res() : rej()
        textArea.remove()
      })
    }
  },
  /**html转义为正常字符串*/
  html2Escape(sHtml) {
    return sHtml.replace(/[<>&"]/g,
      function (c) {return {'<': '&lt;', '>': '&gt;', '&': '&amp;', '"': '&quot;'}[c]}
    )
  },

  /**
   * 打开新页面
   */
  openNewPage([title, url]) {
    layer.open({
      title: title, type: 2, shade: 0.2,
      maxmin: true, move: false, skin: 'self-class',
      shadeClose: true, area: ['90%', '90%'],
      content: ctxUrl + url
    })
  },

  /** Loading消息触发 */
  loadingFun(message = "加载中...") {
    return layer.load(2, {
      shade: [0.1, '#000'],
      success($layer) {
        $layer.css({
          display: "flex", "align-items": "center",
          "flex-direction": "column", "justify-content": "center"
        })
        $layer.append(`<b>${ message }</b>`)
      }
    })
  },

  /** 通过数据产生图标列表DOM */
  generateIcoList(data) {
    const nameKeys = Object.keys(data['name_map'])
    let $icoList = '<div style="width: 480px;display: none;height: 190px;overflow: auto;">'
    for (let nameKey of nameKeys) {
      $icoList += `<h4>${ data['name_map'][nameKey] }</h4>`
      for (let item of data[nameKey]) {
        $icoList += `<i class="fa ${ item }" style="font-size: 18px;margin:5px;width:18px;height:18px;display: inline-block;cursor: pointer;"></i>`
      }
    }
    $icoList += '</div>'
    return $icoList
  },
  generateIcoListPro(data, callback = () => {}) {
    let icoList = this.generateIcoList(data)
    const rootTempDom = document.createElement("div")
    rootTempDom.innerHTML = icoList
    const tempDom = rootTempDom.childNodes[0]
    function toggle() {
      if ('none' === tempDom.style.display) {
        tempDom.style.display = "block"
      } else {
        tempDom.style.display = "none"
      }
    }
    tempDom.addEventListener('click', function (event) {
      const targetEle = event.target
      if ("I" === targetEle.tagName) {
        let at = targetEle.className.split(' ').at(-1)
        callback(at)
        toggle()
      }
    })
    return {dom: tempDom, toggle}
  }
}