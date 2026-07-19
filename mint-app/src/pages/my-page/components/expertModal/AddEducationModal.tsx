import FormField from '@/components/core/form/form-field/FormField';
import Modal from '@/components/core/modal/Modal';
import { AddEducationSchema } from '@/utils/schemas/my-page-schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import MonthYearPicker from './MonthYearPicker';

type AddEducationNumberProps = {
  open: boolean;
  onClose: () => void;
  handleAddEducation: (newEducation: any) => void;
};
type AddEducationFormValue = {
  name: string;
  detail: string;
  // educationDate: (Date | null | undefined)[];
};
const AddEducationModal = ({ open, onClose, handleAddEducation }: AddEducationNumberProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const FORM_ID = 'add_education_number';
  const [dates, setDates] = useState({
    entranceMonth: 0,
    entranceYear: 0,
    graduationMonth: 0,
    graduationYear: 0,
  });

  const { control, handleSubmit, reset } = useForm<AddEducationFormValue>({
    resolver: yupResolver(AddEducationSchema),
  });

  const onSubmit = (data: any) => {
    const educationWithDates = {
      id: `${data.name}-${data.detail}-${data.entranceMonth}-${data.entranceYear}-${data.graduationMonth}-${data.graduationYear}`,
      name: data.name,
      detail: data.detail,
      entranceMonth: dates.entranceMonth,
      entranceYear: dates.entranceYear,
      graduationMonth: dates.graduationMonth,
      graduationYear: dates.graduationYear,
    };
    handleAddEducation(educationWithDates);
    onClose();
  };
  return (
    <Modal
      isDivider={false}
      header={
        <div className='flex flex-col gap-1'>
          <h1 className='font-medium text-lg '>{tCommon('my-page.add_education')}</h1>
          <p className='text-sm text-slate-500'>{tCommon('my-page.add_education_detail')}</p>
        </div>
      }
      className=''
      buttons={[{ children: tCommon('component.save'), variant: 'primary', className: 'w-full mt-1', form: FORM_ID }]}
      open={open}
      onClose={onClose}
      body={
        <div className='flex flex-col gap-6 pt-2'>
          <form id={FORM_ID} onSubmit={handleSubmit(onSubmit)} className='flex flex-col gap-6'>
            <FormField
              className=' flex-col  '
              type='input'
              label={tCommon('my-page.school_name')}
              componentProps={{
                name: 'name',
                control,
                required: true,
                placeholder: tCommon('my-page-placeholder.school_name'),
              }}
            />
            <FormField
              className=' flex-col '
              type='input'
              label={tCommon('my-page.lesson')}
              componentProps={{
                name: 'detail',
                control,
                required: true,
                placeholder: tCommon('my-page-placeholder.lesson'),
              }}
            />
            <MonthYearPicker
              onDateChange={(startMonth, startYear, endMonth, endYear) => {
                setDates((prevDates) => {
                  if (
                    prevDates.entranceMonth !== startMonth ||
                    prevDates.entranceYear !== startYear ||
                    prevDates.graduationMonth !== endMonth ||
                    prevDates.graduationYear !== endYear
                  ) {
                    return {
                      entranceMonth: startMonth,
                      entranceYear: startYear,
                      graduationMonth: endMonth,
                      graduationYear: endYear,
                    };
                  }
                  return prevDates;
                });
              }}
            />
          </form>
        </div>
      }
    />
  );
};
export default AddEducationModal;
