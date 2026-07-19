import { ObituaryDTO } from '@/store/obituary/dto/obituary.dto';
import { orString } from '@/utils/parser/io.parser';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';
import bg from '../../../../public/images/obituary/waving-leaves.gif';
import '../obituary.css';
import SharedModal from './SharedModal';

type ObituaryProps = {
  data?: ObituaryDTO;
  isOwner?: boolean;
};
const Obituary = ({ data, isOwner = true }: ObituaryProps) => {
  const searchParams = useSearchParams();

  const id: string = useMemo(() => {
    return orString(searchParams.get('id'));
  }, [searchParams]);
  const [isOpenShared, setIsOpenShared] = useState<boolean>(false);

  const onShare = () => {
    setIsOpenShared(true);
  };

  const handleKakaoShare = () => {
    if (!(window as any).Kakao) {
      alert('Kakao SDK not initialized');
      return;
    }

    (window as any).Kakao.Share.sendDefault({
      objectType: 'feed',
      content: {
        title: data?.petInformation.name,
        description: `#${data?.petInformation.name.replace(/,\s+/g, '_')} #부고장 #펫포레스트`,
        imageUrl: data?.petImage[0]?.url,
        link: {
          mobileWebUrl: `https://pet-forest.wala-dev.com/obituary/card-guest?id=${id}`,
          webUrl: `https://pet-forest.wala-dev.com/obituary/card-guest?id=${id}`,
        },
      },
      buttons: [
        {
          title: '부고장 보기',
          link: {
            mobileWebUrl: `https://pet-forest.wala-dev.com/obituary/card-guest?id=${id}`,
            webUrl: `https://pet-forest.wala-dev.com/obituary/card-guest?id=${id}`,
          },
        },
      ],
    });
  };

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://t1.kakaocdn.net/kakao_js_sdk/2.7.4/kakao.min.js';
    script.integrity = 'sha384-DKYJZ8NLiK8MN4/C5P2dtSmLQ4KwPaoqAfyA/DfmEc1VDxu4yyC7wy6K1Hs90nka';
    script.crossOrigin = 'anonymous';
    script.onload = () => {
      if ((window as any).Kakao) {
        (window as any).Kakao.init(process.env.NEXT_PUBLIC_KAKAO_KEY);
      }
    };

    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
    };
  }, []);

  return (
    <div
      className='w-full min-h-[700px] flex flex-col items-center pt-[35px] pb-[40px] px-3 relative'
      style={{
        backgroundImage: `url(${bg.src})`,
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
      }}>
      <img className='w-[190px] absolute right-0' src='/images/obituary/leaf-top.png' alt='leaf-top' />
      <img className='w-[150px] absolute left-0 bottom-0' src='/images/obituary/leaf-bot.png' alt='leaf-bottom' />

      <div className='text-[12px] font-medium '>반려동물 부고</div>
      <p className='text-center mt-4 text-[18px] font-medium'>
        반려동물 부고 <br /> 먼저 긴 여행을 떠났습니다.
      </p>

      <div
        className='w-[247px] h-[339px] border-[1.5px] border-green-main rounded-t-[166px] mt-[34px] bg-cover bg-center'
        style={{
          backgroundImage: `url('${data?.petImage[0]?.url}')`,
        }}></div>

      <div className='mt-[24px] text-center font-bold text-[37px] text-green-main'>{data?.petInformation.name}</div>

      {isOwner && (
        <div className='mt-[22px] flex justify-between gap-[10px] w-full'>
          <a href={`card-owner/create?id=${id}`} className='btn-primary-obituary btn-outlined-obituary z-[999]'>
            부고장 수정
          </a>
          <button onClick={onShare} className='btn-primary-obituary'>
            부고장 전송
          </button>
        </div>
      )}

      {!isOwner && (
        <div className=' mt-[46px] text-[20px] font-bold text-grey-sub text-center'>
          <div>{data?.petInformation.endDate ? data.petInformation.endDate.format('YYYY년 MM월 DD일') : ''}</div>
          <p className='text-[18px] font-semibold'>
            무지개다리를 건넌 {data?.petInformation.name}의<br />
            명복을 빌며 함께 기억해주세요.
          </p>
        </div>
      )}

      <SharedModal
        isOpen={isOpenShared}
        onClose={() => {
          setIsOpenShared(false);
        }}
        onKakaoShare={handleKakaoShare}
      />
    </div>
  );
};

export default Obituary;
