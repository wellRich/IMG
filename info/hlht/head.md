<script type="text/javascript">
  document.addEventListener("DOMContentLoaded", function() {

    // 生成目录列表
    var div1 = document.createElement("div");
    div1.style.cssText = "clear:both";
    var outline = document.createElement("div");
    outline.setAttribute("id", "outline-list");
    outline.style.cssText = "border:solid 1px #ccc; background:#eee; min-width:200px;padding:4px 10px;";

    var ele_p = document.createElement("p");
    ele_p.style.cssText = "text-align: left; margin: 0;";
    outline.appendChild(ele_p);

    var ele_span = document.createElement("span");
    // ele_span.style.cssText = "float: left;";
    var ele_text=document.createTextNode("目录");
    ele_span.appendChild(ele_text);

    var ele_a = document.createElement("a");
    ele_a.appendChild(document.createTextNode("[+]"));
    ele_a.setAttribute("href", "#");
    ele_a.setAttribute("onclick", "javascript:return openct(this);");
    ele_a.setAttribute("title", "点击打开目录");

    ele_span.appendChild(ele_a);
    ele_p.appendChild(ele_span);

    var ele_ol = document.createElement("ul");
    ele_ol.style.cssText = "list-style-type:none;display:none;margin-left:14px;padding-left:14px;line-height:160%;";
    ele_ol.setAttribute("id", "outline_ol");
    outline.appendChild(ele_ol);
    var div1 = document.createElement("div");
    div1.style.cssText = "clear:both";

    document.body.insertBefore(outline, document.body.childNodes[0]);
    // 获取所有标题
    var headers = document.querySelectorAll('h1,h2,h3,h4,h5,h6');
    if (headers.length < 2)
      return;

    // -----
    var old_h = 0, ol_cnt = 0;
    // -----

    for (var i = 0; i < headers.length; i++) {

      var ele_ols = null;
      var ele_Current = ele_ol;
      // 找出它是H几，为后面前置空格准备
      var header = headers[i];
      header.setAttribute("id", "t" + i + header.tagName);
      var h = parseInt(header.tagName.substr(1), 10);

      // -----
      if (!old_h){
        old_h = h;

      }

      if (h > old_h) {

        ele_ols = document.createElement("ul");
        ele_Current = ele_ol;
        if(ele_Current && ol_cnt > 0){
          var temp = ol_cnt;
          while(temp > 0){
            ele_Current = ele_Current.lastChild;
            temp--;
          }
        }
        ele_Current.lastChild.appendChild(ele_ols);
        ol_cnt++;
      } else if (h < old_h && ol_cnt > 0) {

        if (h == 1) {
          while (ol_cnt > 0) {
            ol_cnt--;
          }
        } else {
          ele_ols = document.createElement("ul");
          ele_Current = ele_ol;
          if(ele_Current && ol_cnt > 0){
            var temp = ol_cnt;
            while(temp > 1){
              ele_Current = ele_Current.lastChild;
              temp--;
            }
          }
        // var ele_Parent = ele_Current.parentNode();
        //ele_Current.appendChild(ele_ols);
        ol_cnt--;

        }
      } else if (h == old_h && ol_cnt > 0) {

        ele_Current = ele_ol;
        if(ele_Current && ol_cnt > 0){
          var temp = ol_cnt;
          while(temp > 0){
            ele_Current = ele_Current.lastChild;
            temp--;
          }
        }
        ele_Current = ele_Current.lastChild;
      }
      if (h == 1) {
        while (ol_cnt > 0) {
          ol_cnt--;
        }
      }
      if (h < old_h && ol_cnt > 0 && h != 1){
        ele_li = document.createElement("li")
        ele_Current.lastChild.appendChild(ele_li);
        old_h = h;
        var a = document.createElement("a");
        // 为目录项设置链接
        a.setAttribute("href", "#t" + i + header.tagName);
        // 目录项文本前面放置对应的空格
        a.innerHTML = header.textContent;
        ele_li.appendChild(a);
        continue;
      }

      old_h = h;
      // -----
      if (ele_ols){
        ele_li = document.createElement("li")
        ele_ols.appendChild(ele_li); 
      } else {
        ele_li = document.createElement("li")
        ele_Current.appendChild(ele_li);
      }
      var a = document.createElement("a");
      // 为目录项设置链接
      a.setAttribute("href", "#t" + i + header.tagName);
      // 目录项文本前面放置对应的空格
      a.innerHTML = header.textContent;
      ele_li.appendChild(a);
    }
    // -----
    while (ol_cnt > 0) {
      ol_cnt--;
    }
    // -----
    });
function openct(e) {
  if (e.innerHTML == '[+]') {
    // createTextNode
    e.setAttribute('title', '收起');
    e.innerHTML = '[-]';
    var element = document.getElementById("outline_ol");
    element.style.cssText = "margin-left:14px;padding-left:14px;line-height:160%;";
  } else {
    e.setAttribute('title', '展开');
    e.innerHTML = '[+]';
    var element = document.getElementById("outline_ol");
    element.style.cssText = "display:none;margin-left:14px;padding-left:14px;line-height:160%;";
  }
  e.blur();
  return false;
}
</script>