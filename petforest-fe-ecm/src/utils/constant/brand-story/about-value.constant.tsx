import { ReactNode } from 'react';

type AboutValueInfoType = {
  english: string;
  korean: string;
  context: ReactNode;
};

export const ABOUT_VALUE_INFO: AboutValueInfoType[] = [
  {
    english: 'Sincere',
    korean: '진정성',
    context: '반려동물과의 따뜻했던 기억을 진심 어린 마음으로 보듬겠습니다.',
  },
  {
    english: 'Considerate',
    korean: '배려',
    context: '온전한 이별을 위해 마지막 배웅까지 함께하겠습니다.',
  },
  {
    english: 'Trustworthy',
    korean: '신뢰',
    context: (
      <>
        정직하고 반듯한 걸음으로 믿고 맡길 수
        <br />
        있는 장례 의식을 진행하겠습니다.
      </>
    ),
  },
  {
    english: 'Responsible',
    korean: '책임',
    context: (
      <>
        반려동물의 인식 개선을 위해 사회적
        <br />
        책임을 다하겠습니다.
      </>
    ),
  },
];
