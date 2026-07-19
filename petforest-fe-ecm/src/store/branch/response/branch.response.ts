import { Nil } from '@/utils/utils/types/utils.type';

export type BranchRESP = {
  id: number;
  name: string;
  color: Nil<string>;
  roadAddress: Nil<string>;
  streetAddress: Nil<string>;
  naverMap: Nil<string>;
  kakaoMap: Nil<string>;
};
