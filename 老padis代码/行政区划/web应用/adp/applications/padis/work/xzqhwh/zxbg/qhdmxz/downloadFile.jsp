<%@page contentType = "text/html; charset=GBK"  pageEncoding = "GBK"   %> 
<%@page import ="java.io.*, java.util.*, java.text.* "   %> 

<%! 
     /** 
     *  If returns true, then should return a 304 (HTTP_NOT_MODIFIED)
      */ 
     public   static   boolean  checkFor304( HttpServletRequest req,
                                       File file )
    {
         // 
         //   We'll do some handling for CONDITIONAL GET (and return a 304)
         //   If the client has set the following headers, do not try for a 304.
         // 
         //     pragma: no-cache
         //     cache-control: no-cache
         //

         if (  " no-cache " .equalsIgnoreCase(req.getHeader( " Pragma " ))
             ||   " no-cache " .equalsIgnoreCase(req.getHeader( " cache-control " )))
        {
             //  Wants specifically a fresh copy 
        }
         else 
        {
             // 
             //   HTTP 1.1 ETags go first
             //
            String thisTag  =  Long.toString(file.lastModified());

            String eTag  =  req.getHeader(  " If-None-Match "  );

             if ( eTag  !=   null  )
            {
                 if ( eTag.equals(thisTag) )
                {
                     return   true ;
                }
            }

             // 
             //   Next, try if-modified-since
             //
            DateFormat rfcDateFormat  =   new  SimpleDateFormat( " EEE, dd MMM yyyy HH:mm:ss z " );
            Date lastModified  =   new  Date(file.lastModified());

             try 
            {
                 long  ifModifiedSince  =  req.getDateHeader( " If-Modified-Since " );

                 // log.info("ifModifiedSince:"+ifModifiedSince); 
                 if ( ifModifiedSince  !=   - 1  )
                {
                     long  lastModifiedTime  =  lastModified.getTime();

                     // log.info("lastModifiedTime:" + lastModifiedTime); 
                     if ( lastModifiedTime  <=  ifModifiedSince )
                    {
                         return   true ;
                    }
                }
                 else 
                {
                     try 
                    {
                        String s  =  req.getHeader( " If-Modified-Since " );

                         if ( s  !=   null  )
                        {
                            Date ifModifiedSinceDate  =  rfcDateFormat.parse(s);
                             // log.info("ifModifiedSinceDate:" + ifModifiedSinceDate); 
                             if ( lastModified.before(ifModifiedSinceDate) )
                            {
                                 return   true ;
                            }
                        }
                    }
                     catch  (ParseException e)
                    {
                         // log.warn(e.getLocalizedMessage(), e); 
                    }
                }
            }
             catch ( IllegalArgumentException e )
            {
            			e.printStackTrace(); 
                 //  Illegal date/time header format.
                 //  We fail quietly, and return false.
                 //  FIXME: Should really move to ETags. 
            }
        }

         return   false ;
    }
%> 

<% 
     //  String filePath = "c:/�ĵ�.doc";
     //  ����� WEB APP �µ����·���ļ�, ��ʹ�����д���: 
    //String filePath  =  application.getRealPath( "//work//gzdt//tzgggl//upload//2ksp4pro_cn_v615.rar" );
    String filePath = (String)request.getParameter("filePathName");
    
     boolean  isInline  =   false ; //  �Ƿ�����ֱ����������ڴ�(���������ܹ�Ԥ�����ļ�����,
     //  ��ô�ļ�������, �������ʾ����)

     //  ��ջ�����, ��ֹҳ���еĿ���, �ո���ӵ�Ҫ���ص��ļ�������ȥ
     //  �������յĻ��ڵ��� response.reset() ��ʱ�� Tomcat �ᱨ��
     //  java.lang.IllegalStateException: getOutputStream() has already been called for
     //  this response, 
    out.clear();

     //  {{{ BEA Weblogic �ض�
     //  ���� Bea Weblogic ���� "getOutputStream() has already been called for this response"���������
     //  �����ļ�����ʱ�����ļ�������ķ�ʽ����
     //  ����response.reset()���������еģ�>���治Ҫ���У��������һ����
     //  ��ΪApplication Server�ڴ������jspʱ���ڣ�>��<��֮�������һ����ԭ�����������Ĭ����PrintWriter��
     //  ����ȴҪ�����������ServletOutputStream���������൱����ͼ��Servlet��ʹ������������ƣ�
     //  �ͻᷢ����getOutputStream() has already been called for this response�Ĵ���
     //  ��ϸ�����More Java Pitfill��һ��ĵڶ����� Web��Item 33����ͼ��Servlet��ʹ������������� 270
     //  ��������л��У������ı��ļ�û��ʲô���⣬���Ƕ���������ʽ������AutoCAD��Word��Excel���ļ�
     // �����������ļ��оͻ���һЩ���з�0x0d��0x0a���������ܵ���ĳЩ��ʽ���ļ��޷��򿪣���ЩҲ���������򿪡�
     //  ͬʱ���ַ�ʽҲ����ջ�����, ��ֹҳ���еĿ��е����������������ȥ 
    response.reset();
     //  }}} 

     try  {
        java.io.File f  =   new  java.io.File(filePath);
         if  (f.exists()  &&  f.canRead()) {
             //  ����Ҫ���ͻ��˵Ļ������Ƿ��Ѿ����˴��ļ������°汾, ��ʱ��͸���
             //  �ͻ�����������������, ��Ȼ���������Ҳû�й�ϵ 
             if ( checkFor304( request, f ) )
            {
                 //  �ͻ����Ѿ��������°汾, ���� 304 
                response.sendError( HttpServletResponse.SC_NOT_MODIFIED );
                 return ;
            }

             //  �ӷ���������������ȡ�ļ��� contentType �����ô�contentType, ���Ƽ�����Ϊ
             //  application/x-download, ��Ϊ��ʱ�����ǵĿͻ����ܻ�ϣ�����������ֱ�Ӵ�,
             //  �� Excel ����, ���� application/x-download Ҳ����һ����׼�� mime type,
             //  �ƺ� FireFox �Ͳ���ʶ���ָ�ʽ�� mime type 
            String mimetype  =   null ;
            mimetype  =  application.getMimeType( filePath );
             if ( mimetype  ==   null  )
            {
                mimetype  =   "application/octet-stream;charset=ISO8859-1" ;
            }

            response.setContentType( mimetype );

             //  IE �Ļ���ֻ���� IE ����ʶ��ͷ�������� HTML �ļ�, ���� IE �ض�Ҫ�򿪴��ļ�! 
            String ua  =  request.getHeader( " User-Agent " ); //  ��ȡ�ն����� 
             if (ua  ==   null ) ua  =   " User-Agent: Mozilla/4.0 (compatible; MSIE 6.0;) " ;
             boolean  isIE  =  ua.toLowerCase().indexOf( " msie " )  !=   - 1 ; //  �Ƿ�Ϊ IE 

             if (isIE  &&   ! isInline) {
                mimetype  =   " application/x-msdownload " ;
            }


             //  �������ǽ��跨�ÿͻ��˱����ļ���ʱ����ʾ��ȷ���ļ���, ������ǽ��ļ���
             //  ת��Ϊ ISO8859-1 ���� 
            String downFileName  =   new  String(f.getName().getBytes(),  "ISO8859-1" );

            String inlineType  =  isInline  ?   " inline "  :  " attachment " ; //  �Ƿ���������

             //  or using this, but this header might not supported by FireFox
             // response.setContentType("application/x-download"); 
            response.setHeader ("Content-Disposition",inlineType+";filename=\""
             +  downFileName  +   "\""); 

            response.setContentLength((int)f.length()); //  �����������ݴ�С 

             byte[] buffer  =  new byte[4096]; //  ������ 
            BufferedOutputStream output  =   null ;
            BufferedInputStream input  =   null ;

             //
             try  {
                output  =   new  BufferedOutputStream(response.getOutputStream());
                input  =   new  BufferedInputStream( new  FileInputStream(f));

                 int  n  =  ( - 1 );
                 while  ((n  =  input.read(buffer,  0 ,  4096 ))  >   - 1 ) {
                    output.write(buffer,  0 , n);
                }
                response.flushBuffer();
            }
             catch  (Exception e) {
            }  //  �û�����ȡ�������� 
             finally  {
                 if  (input  !=   null ) input.close();
                 if  (output  !=   null ) output.close();
            }

        }
         return ;
    }  catch  (Exception ex) {
        ex.printStackTrace(); 
    }
     //  �������ʧ���˾͸����û����ļ������� 
    response.sendError( 404 );
%> 