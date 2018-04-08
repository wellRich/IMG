<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %> 

<%@ page import="java.util.*" %>
<% 

Map treeMap=(Map)request.getAttribute("map");
String tree_content = (String)treeMap.get("TREE");

tree_content = tree_content.replaceAll("action_prefix_","javascript:subQuery('");
tree_content = tree_content.replaceAll("_action_suffix","')");

tree_content =  tree_content.replaceAll("src_prefix_","BgdzblrService.createTjXzqhTree.do?XZQH_DM=");
tree_content = tree_content.replaceAll("_src_suffix","");


response.setContentType("text/xml;charset=UTF-8");
out.println(tree_content); 
%>