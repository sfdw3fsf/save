'use client';
import { useGlobalStore } from '@/data/global/global.store';
import useResize from '@/hook/useResize';
import { getPopupApi, popupKeys } from '@/store/popup/popup.api';
import { popupRespToDto } from '@/store/popup/popup.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { Modal } from 'antd';
import dayjs from 'dayjs';
import { useEffect, useRef, useState } from 'react';

const HomeIntro = () => {
  const { setIsIntro } = useGlobalStore();
  const { isMobile, isTablet, isResponsive } = useResize();
  const [visibleDiv, setVisibleDiv] = useState<number>(0);
  const scrollTimeoutRef = useRef<NodeJS.Timeout | null>(null);
  const touchStartYRef = useRef<number>(0);
  const [isScrolling, setIsScrolling] = useState<boolean>(false);
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const { data: popupData } = useQuery({
    queryKey: popupKeys.lists(),
    queryFn: () => getPopupApi({ date: dayjs().valueOf() }),
    select: (response) => {
      return response.data.data[0] && popupRespToDto(response.data.data[0]);
    },
    placeholderData: keepPreviousData,
  });

  const handleScroll = (deltaY: number) => {
    if (isScrolling) return;
    //Debounce to prevent calling many times with touchpad
    setIsScrolling(true);

    if (deltaY > 0) {
      // Scroll down
      setVisibleDiv((prev) => Math.min(prev + 1, 3));
    } else if (deltaY < 0) {
      // Scroll up
      setVisibleDiv((prev) => Math.max(prev - 1, 0));
    }

    // Unlock scrolling after a short delay to allow for smooth transition
    setTimeout(() => {
      setIsScrolling(false);
    }, 1500);
  };

  const handleWheel = (event: WheelEvent) => {
    event.preventDefault();
    handleScroll(event.deltaY);
  };

  const handleTouchStart = (event: TouchEvent) => {
    touchStartYRef.current = event.touches[0].clientY;
  };

  const handleTouchMove = (event: TouchEvent) => {
    const touchEndY = event.touches[0].clientY;
    const deltaY = touchStartYRef.current - touchEndY;
    handleScroll(deltaY);
    event.preventDefault();
  };

  const onClose = () => {
    setIsOpen(false);
  };

  const hidePopupForToday = () => {
    const today = dayjs().format('YYYY-MM-DD');
    localStorage.setItem('hidePopupUntil', today);
    setIsOpen(false);
  };

  useEffect(() => {
    if (isOpen) {
      return;
    }
    if (isMobile || isTablet) {
      window.addEventListener('touchstart', handleTouchStart);
      window.addEventListener('touchmove', handleTouchMove, { passive: false });
    } else {
      window.addEventListener('wheel', handleWheel, { passive: false });
    }

    return () => {
      if (isMobile || isTablet) {
        window.removeEventListener('touchstart', handleTouchStart);
        window.removeEventListener('touchmove', handleTouchMove);
      } else {
        window.removeEventListener('wheel', handleWheel);
      }

      if (scrollTimeoutRef.current !== null) {
        clearTimeout(scrollTimeoutRef.current);
      }
    };
  }, [isMobile, isTablet, isScrolling, isOpen]);

  useEffect(() => {
    if (visibleDiv === 3) {
      setTimeout(() => {
        setIsIntro(false);
      }, 600);
    }
    if (visibleDiv === 1 && isResponsive === true) {
      setTimeout(() => {
        setIsIntro(false);
      }, 600);
    }
  }, [visibleDiv]);

  useEffect(() => {
    if (popupData) {
      setIsOpen(true);
    }
  }, [popupData]);

  useEffect(() => {
    const hideUntilDate = localStorage.getItem('hidePopupUntil');
    const today = dayjs().format('YYYY-MM-DD');
    if (hideUntilDate === today) {
      setIsOpen(false);
    } else if (popupData) {
      setIsOpen(true);
    }
  }, [popupData]);

  return (
    <div className='h-[100vh] relative w-full z-10 bg-black'>
      <div className={`intro-section ${visibleDiv === 0 ? 'active' : ''}`}></div>
      <div className={`intro-section ${visibleDiv === 1 ? 'active' : ''}`}>
        <div className='flex items-center justify-center h-full w-full'>
          {!isResponsive && (
            <p className={`w-[90%] text-center text-[4vmax] text-white z-[2] font-siena`}>Everlasting Memories</p>
          )}
        </div>
      </div>
      <div className={`intro-section ${visibleDiv === 2 ? 'active' : ''}`}>
        <div className='flex items-center justify-center h-full w-full'>
          {!isResponsive && (
            <p className='w-[90%] text-center font-medium text-[3vmax] text-white z-[2]'>함께한 순간을 영원히 기억합니다.</p>
          )}
        </div>
      </div>
      <div className={`intro-section ${visibleDiv === 3 ? 'active' : ''}`}></div>
      <div className='mouse-wheel'>
        <span className='wheel-one'></span>
        <span className='wheel-two'></span>
        <span className='wheel-three'></span>
        <p className='text-[14px] text-[#f8f9fa] mt-4 tracking-wider leading-[23px] w-full'>SCROLL</p>
      </div>

      {popupData?.imageUrl && (
        <Modal
          open={isOpen}
          styles={{ body: { padding: 0 } }}
          onCancel={onClose}
          onClose={onClose}
          transitionName=''
          maskTransitionName=''
          footer={null}
          centered
          closable={false}
          className='!w-[90%] max-w-[563px]'>
          <div className='w-full h-[340px] bg-white '>
            <img src={popupData.imageUrl} className='w-full h-[302px] object-contain' />
            <div className='flex w-full justify-between mt-3'>
              <button onClick={hidePopupForToday} className='flex-1  !h-[40px]'>
                오늘하루 그만보기
              </button>
              <button onClick={onClose} className='flex-1  !h-[40px]'>
                닫기
              </button>
            </div>
          </div>
        </Modal>
      )}
    </div>
  );
};

export default HomeIntro;
