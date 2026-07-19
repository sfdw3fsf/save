import { Nil } from '@/utils/types/utils.type';

export type ManagerBranchRESP = {
  id: number;
  name: Nil<string>;
  color: Nil<string>;
  roadAddress: Nil<string>;
  streetAddress: Nil<string>;
  naverMap: Nil<string>;
  kakaoMap: Nil<string>;
};
