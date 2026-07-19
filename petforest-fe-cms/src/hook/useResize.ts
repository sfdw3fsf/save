import { useLayoutEffect, useMemo, useState } from 'react';

const useResize = () => {
  const [size, setSize] = useState({ width: 0, height: 0 });

  useLayoutEffect(() => {
    const getSize = () => setSize({ width: window.innerWidth, height: window.innerHeight });
    getSize();
    window.addEventListener('resize', getSize);
    return () => window.removeEventListener('resize', getSize);
  }, []);

  // Ensure the hooks are always evaluated
  const isMobile = useMemo(() => size.width > 0 && size.width <= 500, [size.width]);
  const isTablet = useMemo(() => size.width > 500 && size.width <= 768, [size.width]);
  const isResponsive = useMemo(() => size.width > 0 && size.width < 1024, [size.width]);

  return { ...size, isMobile, isTablet, isResponsive };
};

export default useResize;
