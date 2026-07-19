'use client';
import BrandStoryBanner from '@/components/brand-story/BrandStoryBanner';
import RellaxFigure from '@/components/brand-story/ImageBrandStoryBanner';
import ValueInfomationHover from '@/components/brand-story/ValueInfomationHover';
import '@/components/brand-story/brand-story.css';
import PageContainer from '@/components/common/page-container/PageContainer';
import { ABOUT_VALUE_INFO } from '@/utils/constant/brand-story/about-value.constant';
import { useEffect } from 'react';

const BrandStoryPage = () => {
  useEffect(() => {
    const bodyElement = document.body;
    const motionElement = document.getElementById('motion');
    const pElements = motionElement ? motionElement.querySelectorAll('p') : [];

    const observer = new IntersectionObserver(
      (entries) => {
        const entry = entries[0];

        if (entry.isIntersecting) {
          bodyElement.classList.add('green');
          pElements.forEach((p) => p.classList.add('act'));
        } else {
          bodyElement.classList.remove('green');
          pElements.forEach((p) => p.classList.remove('act'));
        }
      },
      { threshold: [0.1, 0] },
    );

    if (motionElement) {
      observer.observe(motionElement);
    }
    return () => {
      if (motionElement) {
        observer.unobserve(motionElement);
      }
    };
  }, []);

  return (
    <PageContainer>
      <div id='about'>
        <BrandStoryBanner />
        <div className='sec !mb-0' id='about-body'>
          <div className='sec-inner'>
            <div className='about-cont-inner'>
              <RellaxFigure
                src='/images/about/brand-story/brand-story-image1.jpg'
                alt=''
                rellaxSpeed='4'
                rellaxXsSpeed='-1'
                id='img1'
              />
              <RellaxFigure
                src='/images/about/brand-story/brand-story-image2.png'
                alt=''
                rellaxSpeed='1'
                rellaxXsSpeed='1'
                id='img2'
              />
              <RellaxFigure
                src='/images/about/brand-story/brand-story-image3.jpg'
                alt=''
                rellaxSpeed='3'
                rellaxXsSpeed='-2'
                id='img3'
              />
              <div className='sec-copy md:text-[24px] text-[17px]'>
                <p>우리가 처음 만났던 날.</p>
                <p>아이에게 이름을 지어준 순간부터 우리의 교감은 시작되었습니다.</p>
                <p>말 한마디 없이, 눈빛만으로도 우리는 많은 얘기를 나눌 수 있었습니다.</p>
              </div>
              <RellaxFigure
                src='/images/about/brand-story/brand-story-image4.png'
                alt=''
                rellaxSpeed='1'
                rellaxXsSpeed='4'
                rellaxPercentage='0.5'
                id='img4'
              />
              <RellaxFigure
                src='/images/about/brand-story/brand-story-image5.png'
                alt=''
                rellaxSpeed='-2'
                rellaxXsSpeed='1'
                id='img5'
              />
              <RellaxFigure
                src='/images/about/brand-story/brand-story-image6.png'
                alt=''
                rellaxSpeed='2'
                rellaxXsSpeed='-2'
                rellaxPercentage='0.5'
                id='img6'
              />
              <div className='sec-copy !mt-[20vh] md:text-[24px] text-[17px]'>
                <p>서로 위로가 되어주고, 웃게 해주며 그 자리에 있는 것만으로도</p>
                <p>한없는 행복이 차오르던 시간.</p>
                <p>우리 아이가 내게로 온 건 실로 어마어마한 기적이었습니다.</p>
              </div>
            </div>
            <div id='motion'>
              <p className='p md:!text-[24px] '>
                <>
                  펫포레스트는 기적 같은 생을 살다간
                  <br className='block lg:hidden' />
                  우리 아이의 마지막 보금자리이자,
                </>
              </p>
              <p className='p text-right md:!text-[24px]'>
                <>
                  한 생명의 시작과 끝을 지켜준 당신을
                  <br className='block lg:hidden' />
                  위한 위로의 공간입니다.
                </>
              </p>
              <p className='p md:!text-[24px]'>
                <>
                  아이의 따스한 온기를 기억하는
                  <br className='block lg:hidden' />
                  &apos;기억의 숲&apos;입니다.
                </>
              </p>
            </div>
            <div className='about-cont-inner'>
              <div className='sec-copy md:text-[24px] text-[17px] flex flex-col gap-[30px] items-center'>
                <div>한 송이 한 송이 진심 어린 마음들이 모여 나무를 만들고</div>
                <div>초록의 나무가 울창한 숲이 되어 그늘을 드리우는 것처럼</div>
                <div>우리가 오래전 나눈 이야기들 역시</div>
                <div>계절이 오가는 동안 늘 푸른 기억으로 도착해 있을 것입니다.</div>
              </div>
            </div>
          </div>
        </div>
        <div className='sec !mb-0' id='about-value'>
          <div className='sec-inner lmt maxw border-none'>
            <div className='sec-title'>
              <h2>펫포레스트가 소중히 여기는 가치</h2>
              <p>
                펫포레스트는 생명 존중의 정신을 바탕으로 올바른 반려동물 장례 문화를 이끌고, 반려동물에 대한 사회적 가치를
                실현합니다.
              </p>
            </div>
            <div className='sec-body'>
              <ul className='exw flex flex-wrap justify-center'>
                {ABOUT_VALUE_INFO.map((item, index) => (
                  <ValueInfomationHover key={index} english={item?.english} korean={item?.korean} context={item?.context} />
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </PageContainer>
  );
};

export default BrandStoryPage;
