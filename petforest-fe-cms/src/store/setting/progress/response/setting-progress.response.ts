import { Nil } from '@/utils/types/utils.type';

export type SettingProgress = {
  id: number;
  name: Nil<string>;
  ordinal: Nil<string>;
};

export type SettingProgressRESP = {
  subProgresses: SettingProgress[];
} & SettingProgress;

export type SettingSubProgressRESP = SettingProgress;
