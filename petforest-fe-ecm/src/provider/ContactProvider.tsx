import { FloatButton } from 'antd';
import Image from 'next/image';
import Script from 'next/script';

const ContactProvider = () => {
  return (
    <>
      <a href='tel:1577-0996' className='relative'>
        <FloatButton
          icon={<Image src={'/icon/icon-phone.svg'} alt='phone' fill />}
          className='bottom-[70px] right-[14px] w-[44px] h-[44px]  sm:bottom-[90px]  sm:w-[55px] sm:h-[55px]  sm:right-[25px]'
        />
      </a>
      <Script
        id='channel-talk-script'
        strategy='afterInteractive'
        dangerouslySetInnerHTML={{
          __html: `
             (function(){
               var w=window;if(w.ChannelIO){return w.console.error("ChannelIO script included twice.");}
               var ch=function(){ch.c(arguments);};ch.q=[];ch.c=function(args){ch.q.push(args);};
               w.ChannelIO=ch;function l(){
                 if(w.ChannelIOInitialized){return;}
                 w.ChannelIOInitialized=true;
                 var s=document.createElement("script");s.type="text/javascript";s.async=true;
                 s.src="https://cdn.channel.io/plugin/ch-plugin-web.js";
                 var x=document.getElementsByTagName("script")[0];
                 if(x.parentNode){x.parentNode.insertBefore(s,x);}
               }
               if(document.readyState==="complete"){l();}
               else{w.addEventListener("DOMContentLoaded",l);w.addEventListener("load",l);}
             })();
             ChannelIO('boot', {
               "pluginKey": "c11517ef-a25d-4eec-8b9d-b1b793dc5ab1"
             });
           `,
        }}
      />
    </>
  );
};

export default ContactProvider;
