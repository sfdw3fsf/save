import { useCallback, useEffect, useState } from 'react';

const useDaumnAddress = () => {
  const [scriptLoaded, setScriptLoaded] = useState<boolean>(false);

  const openPostcode = useCallback(() => {
    if (!scriptLoaded) {
      console.error('Daum Postcode script not yet loaded');
      return Promise.reject('Script not loaded');
    }

    return new Promise<any>((resolve, reject) => {
      try {
        new (window as any).daum.Postcode({
          oncomplete: (data: any) => {
            resolve(data);
          },
          onclose: () => {
            reject('Postcode selection was closed');
          },
        }).open();
      } catch (error) {
        reject('Failed to open Daum Postcode');
      }
    });
  }, [scriptLoaded]);

  useEffect(() => {
    const script = document.createElement('script');
    script.src = '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
    script.async = true;
    script.onload = () => {
      console.log('Daum Postcode script loaded');
      setScriptLoaded(true);
    };
    script.onerror = () => {
      console.error('Failed to load Daum Postcode script');
    };
    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
      setScriptLoaded(false);
    };
  }, []);

  return { openPostcode };
};

export default useDaumnAddress;
