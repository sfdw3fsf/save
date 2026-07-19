import FormField from '@/components/core/form/form-field/FormField';
import Modal from '@/components/core/modal/Modal';
import { AddExperienceSchema } from '@/utils/schemas/my-page-schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import MonthYearPicker from './MonthYearPicker';

type AddExperienceModalProps = {
  open: boolean;
  onClose: () => void;
  handleAddExperience: (newExperience: any) => void;
};

type AddExperienceFormValue = {
  name: string;
  detail: string;
};

const AddExperienceModal = ({ open, onClose, handleAddExperience }: AddExperienceModalProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');
  const FORM_ID = 'add_experience_form';

  // Initialize the dates state
  const [dates, setDates] = useState({
    startMonth: 0,
    startYear: 0,
    endMonth: 0,
    endYear: 0,
  });

  // Hook form setup
  const { control, handleSubmit } = useForm<AddExperienceFormValue>({
    resolver: yupResolver(AddExperienceSchema),
  });

  const onSubmit = (data: AddExperienceFormValue) => {
    const experienceWithDates = {
      id: `${data.name}-${data.detail}-${dates.startMonth}-${dates.startYear}-${dates.endMonth}-${dates.endYear}`,
      name: data.name,
      detail: data.detail,
      startMonth: dates.startMonth,
      startYear: dates.startYear,
      endMonth: dates.endMonth,
      endYear: dates.endYear,
    };
    handleAddExperience(experienceWithDates);
    onClose();
  };

  return (
    <Modal
      isDivider={false}
      className=''
      header={
        <div className='flex flex-col gap-1'>
          <h1 className='font-medium text-lg '>{tCommon('my-page.add_experience')}</h1>
          <p className='text-sm text-slate-500'>{tCommon('my-page.edit_experience_label')}</p>
        </div>
      }
      buttons={[{ children: tCommon('component.save'), variant: 'primary', form: FORM_ID, className: 'w-full mt-1' }]}
      open={open}
      onClose={onClose}
      body={
        <div className='flex flex-col gap-6 pt-2'>
          <form id={FORM_ID} onSubmit={handleSubmit(onSubmit)} className='flex flex-col gap-6'>
            <FormField
              className='flex-col'
              type='input'
              label={tForm('label.affiliation')}
              componentProps={{
                name: 'name',
                control,
                required: true,
                placeholder: tForm('placeholder.affiliation_experience'),
              }}
            />
            <FormField
              className='flex-col'
              type='input'
              label={tForm('label.department')}
              componentProps={{
                name: 'detail',
                control,
                required: true,
                placeholder: tForm('placeholder.department_my_page'),
              }}
            />
            <MonthYearPicker
              isCurrentWork={true}
              onDateChange={(startMonth, startYear, endMonth, endYear) => {
                setDates((prevDates) => {
                  if (
                    prevDates.startMonth !== startMonth ||
                    prevDates.startYear !== startYear ||
                    prevDates.endMonth !== endMonth ||
                    prevDates.endYear !== endYear
                  ) {
                    return {
                      startMonth,
                      startYear,
                      endMonth,
                      endYear,
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

export default AddExperienceModal;
