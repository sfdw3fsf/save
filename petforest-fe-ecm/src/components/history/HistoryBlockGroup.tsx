'use client';
import { BlockGroupInfoType } from '@/utils/constant/history/block-group.constant';
import { useEffect, useRef, useState } from 'react';

type BlockGroupProps = BlockGroupInfoType;

const BlockGroup = ({ year = '', context = [] }: BlockGroupProps) => {
  const [isVisible, setIsVisible] = useState(false);
  const ref = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            setIsVisible(true);
          } else {
            setIsVisible(false);
          }
        });
      },
      {
        threshold: 0.1,
      },
    );

    if (ref.current) {
      observer.observe(ref.current);
    }

    return () => {
      if (ref.current) {
        observer.unobserve(ref.current);
      }
    };
  }, []);
  return (
    <div ref={ref} className={`wp-block-group ${isVisible ? 'on' : ''}`}>
      <div className='wp-block-group__inner-container'>
        <h2 className='sr font-medium md:!text-[30px] !text-[25px] font-siena'>{year}</h2>

        <ul>
          {context.length > 0 &&
            context.map((subtext, index) => (
              <li className='md:!text-[16px] !text-[13px]' key={index}>
                {subtext}
              </li>
            ))}
        </ul>

        <p></p>
      </div>
    </div>
  );
};

export default BlockGroup;
