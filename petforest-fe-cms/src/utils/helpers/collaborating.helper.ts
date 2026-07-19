import { HospitalDeliverySummaryDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { TFunction } from 'i18next';

export const helperFormatCountData = (
  path: string,
  data: HospitalDeliverySummaryDTO,
  tFunc: TFunction<'collaborating_delivery_promotional_material', undefined>,
) => {
  return Object.entries(data)
    .filter(([key]) => key !== 'total')
    .map(([sectionKey, sectionData]) => ({
      label: tFunc(`${path}.${sectionKey}` as any),
      value: Object.entries(sectionData).map(([key, value]) => ({
        title: tFunc(`${path}.${key}` as any),
        value: value,
      })),
    }));
};
