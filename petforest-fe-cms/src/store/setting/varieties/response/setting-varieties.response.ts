import { Nil } from '@/utils/types/utils.type';

export type SettingVarietiesRESP = {
  id: number;
  name: Nil<string>;
  petBreeds: {
    id: number;
    name: string;
    ordinal: number;
  }[];
  ordinal: Nil<string>;
};

export type SettingVarietiesBreedNameRESP = {
  id: number;
  name: string;
};
