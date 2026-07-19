import dayjs from 'dayjs';
import { useEffect, useMemo, useRef, useState } from 'react';

const useCountdown = (initialSeconds: number) => {
  const [totalSeconds, setTotalSeconds] = useState<number>(0);
  const intervalRef = useRef<NodeJS.Timeout | null>(null);

  const startCountdown = () => {
    if (totalSeconds === 0) {
      setTotalSeconds(initialSeconds);
    }
    clearExistingInterval();
    intervalRef.current = setInterval(() => {
      setTotalSeconds((prev) => {
        if (prev > 1) return prev - 1;
        clearExistingInterval();
        return 0;
      });
    }, 1000);
  };

  const stopCountdown = () => {
    clearExistingInterval();
    setTotalSeconds(0);
  };

  const clearExistingInterval = () => {
    if (intervalRef.current) {
      clearInterval(intervalRef.current);
      intervalRef.current = null;
    }
  };

  const isCounting = useMemo<boolean>(() => totalSeconds > 0 && intervalRef.current !== null, [totalSeconds]);

  useEffect(() => {
    return () => clearExistingInterval();
  }, []);

  const formatTime = (totalSeconds: number) => {
    const mins = Math.floor(totalSeconds / 60);
    const secs = totalSeconds % 60;
    return dayjs().minute(mins).second(secs).format('mm:ss');
  };

  return { totalSeconds, startCountdown, stopCountdown, formatTime, isCounting };
};

export default useCountdown;
