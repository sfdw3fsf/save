import { OPTION_GUARDIAN_NUMBER_VISITOR } from '../constant/reservation/menu-pet-form.constant';
import { priorityOrder } from '../constant/reservation/reservation-info.constant';
import { VISTOR_NUMBER } from '../enums/reservation.enum';

export const getErrorMessages = (errorObj: any): string => {
  for (const path of priorityOrder) {
    const keys = path.split('.');
    let current: any = errorObj;

    for (const key of keys) {
      current = current ? current[key] : null;
    }

    if (current && current.message) {
      return current.message;
    }
  }

  return '';
};

export const getVisitorLabelByValue = (value?: VISTOR_NUMBER): string => {
  const option = OPTION_GUARDIAN_NUMBER_VISITOR.find((item) => item.value === value);
  return option ? option.label : '';
};
