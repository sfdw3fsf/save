import { ReactNode } from 'react';

type PageTitleBannerProps = {
  title: string;
  subTitle?: ReactNode;
  img: string;
  children?: React.ReactNode;
};

const PageTitleBanner = ({ title, subTitle, img, children }: PageTitleBannerProps) => {
  return (
    <div className='cont-head' style={{ backgroundImage: `url(${img})` }}>
      <article>
        <p className='title !mb-[13px] md:!mb-[22px]'>{title}</p>
        <p className='sub-title lg:!mt-[20px] !mt-[15px] !mb-[35px]'>{subTitle}</p>
      </article>
      {children}
    </div>
  );
};

export default PageTitleBanner;
