/* eslint-disable jsx-a11y/anchor-is-valid */
import { faqKeys, getReviewListApi } from '@/store/customer-service/customer-service.api';
import { reviewRespToDto } from '@/store/customer-service/customer-service.service';
import { HOME_SERVICE_BANNERS } from '@/utils/constant/home/home-service-banner.constant';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import Slider, { Settings } from 'react-slick';

const HomeBanner = () => {
  const settings: Settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  const { data: reviewList } = useQuery({
    queryKey: faqKeys.lists(),
    queryFn: () => getReviewListApi(),
    select: (response) => {
      return reviewRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  return (
    <div className='sec md:!mb-[150px]'>
      <div className='sec-inner'>
        <div className='lmt maxw'>
          {HOME_SERVICE_BANNERS.map((banner, index) => (
            <div key={index} className={`service-banner-container ${index === 1 || index === 3 ? 'reverse' : ''}`}>
              <div className='flex-1'>
                <img src={banner.img} alt='' />
              </div>
              <div className='flex-1 '>
                <div className='service-banner-content'>
                  <p className='home-banner-title'>{banner.title}</p>
                  <p className='home-banner-content whitespace-normal sm:whitespace-break-spaces'>{banner.content}</p>
                  <a href={banner.link} className='btn-home-feature'>
                    {banner.btnText}
                  </a>
                </div>
              </div>
            </div>
          ))}
        </div>
        <div className='home-review-banner md:mt-5'>
          <img src='/images/home/home-review-banner.jpg' alt='' />
          <div className='z-[1] text-white w-full font-siena'>
            <p className='text-center text-[25px] md:text-[36px] leading-[1.3]'>Everlasting Memories,</p>
            <p className='text-center text-[25px] md:text-[36px] leading-[1.3]'>Pet Forest</p>
          </div>
        </div>
        <div className='maxw lmt pt-[100px] md:pt-[120px]'>
          <div className='max-w-[50%] mx-auto mb-10 text-center sec-head md:!mb-20'>
            <h2 className='home-banner-title'>펫포레스트 장례 이용 후기</h2>
            <p className='mt-[10px] leading-[1.68] !mb-0 md:mt-5 whitespace-normal sm:whitespace-break-spaces'>
              펫포레스트를 먼저 이용하신 보호자님의 이야기를 모았습니다. 항상 고객의 소리에 귀기울이는 펫포레스트가
              {'\n'}
              되겠습니다.
            </p>
          </div>
          <div className='home-slider-container'>
            <Slider {...settings}>
              {reviewList?.map((review) => (
                <div key={review.id} className='home-review-container'>
                  {review.reviews.map((detail, index) => (
                    <div key={index} className='home-review-item'>
                      <p className='whitespace-pre-line text-[23px] leading-[25px] font-bold'>{detail.title}</p>
                      <p className='mt-4 mb-10 text-[17px] font-medium text-[#999999] leading-[22px]'>{detail.content}</p>
                      <p className='mt-[30px] mb-10 text-[15px] font-medium text-[#999999] leading-[14px]'>
                        {detail.reviewerName}
                      </p>
                    </div>
                  ))}
                </div>
              ))}
            </Slider>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomeBanner;
