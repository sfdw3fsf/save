import useResize from '@/hook/useResize';
import { TextProps } from 'antd/es/typography/Text';
import { ReactNode } from 'react';
import CustomText from '../Text';

type PageTitleProps = {
  title: string;
  actions?: ReactNode;
  isWrap?: boolean;
  classNameExtra?: string;
  prefix?: ReactNode;
} & Omit<TextProps, 'prefix'>;

const PageTitle = ({ title, actions, isWrap = false, prefix, classNameExtra, ...rest }: PageTitleProps) => {
  const { isMobile } = useResize();
  return (
    <div className={`flex items-center justify-between ${isWrap && 'flex-wrap gap-3'}`}>
      <div className='flex gap-[3px] items-center'>
        {prefix}
        <CustomText variant={isMobile ? 'body-1-normal' : 'title-3'} className='!font-semibold whitespace-nowrap' {...rest}>
          {title}
        </CustomText>
      </div>
      <div className={`flex flex-wrap justify-end gap-2 md:gap-3 ${classNameExtra}`}>{actions}</div>
    </div>
  );
};

export default PageTitle;
