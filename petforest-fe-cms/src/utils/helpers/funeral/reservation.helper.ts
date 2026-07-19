import { TFunction } from 'i18next';

export const helperFuneralServiceDetailOption = (
  path: string,
  enumObj: Record<string, string>,
  tReservation: TFunction<'funeral_reservation', undefined>,
) => {
  return Object.values(enumObj).map((item) => {
    const itemParts = item.split('_');
    return {
      label: `${tReservation(`${path}.${itemParts[0]}` as any)} [${tReservation(`${path}.${itemParts[1]}` as any)}]`,
      value: item,
    };
  });
};

export const helperNumberPersonOption = (
  path: string,
  number: number,
  tReservation: TFunction<'funeral_reservation', undefined>,
) => {
  return Array.from({ length: number }, (_, index) => {
    return {
      label: `${index + 1} ${tReservation(path as any)}`,
      value: index + 1,
    };
  });
};
