import FormField from '@/components/core/form/form-field/FormField';
import Modal from '@/components/core/modal/Modal';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import MonthYearPicker from './MonthYearPicker';

type EditExperienceModalProps = {
  open: boolean;
  onClose: () => void;
  handleEditExperience: (updatedExperience: any) => void;
  handleDeleteExperience: (id: string | number) => void;
  initialValues: {
    id: string | number;
    name: string;
    detail: string;
    startMonth?: number;
    startYear?: number;
    endMonth?: number;
    endYear?: number;
  };
};

type EditExperienceFormValue = {
  name: string;
  detail: string;
};

const EditExperienceModal = ({
  open,
  onClose,
  handleEditExperience,
  initialValues,
  handleDeleteExperience,
}: EditExperienceModalProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const FORM_ID = 'edit_experience_modal';
  const { t: tForm } = useTranslation('form');
  const [dates, setDates] = useState({
    startMonth: initialValues.startMonth,
    startYear: initialValues.startYear,
    endMonth: initialValues.endMonth,
    endYear: initialValues.endYear,
  });

  const { control, handleSubmit, reset } = useForm<EditExperienceFormValue>({
    defaultValues: {
      name: initialValues.name,
      detail: initialValues.detail,
    },
  });

  useEffect(() => {
    // Reset form and date values when initialValues change
    if (initialValues) {
      reset({
        name: initialValues.name,
        detail: initialValues.detail,
      });
      setDates({
        startMonth: initialValues.startMonth,
        startYear: initialValues.startYear,
        endMonth: initialValues.endMonth,
        endYear: initialValues.endYear,
      });
    }
  }, [initialValues, reset]);

  const onSubmit = (data: EditExperienceFormValue) => {
    const experienceWithDates = {
      id: initialValues.id,
      name: data.name,
      detail: data.detail,
      startMonth: dates.startMonth,
      startYear: dates.startYear,
      endMonth: dates.endMonth,
      endYear: dates.endYear,
    };
    handleEditExperience(experienceWithDates);
    onClose();
  };

  const onDelete = () => {
    handleDeleteExperience(initialValues.id);
    onClose();
  };

  return (
    <Modal
      className=''
      buttons={[
        { children: tCommon('my-page.delete_action'), onClick: onDelete, className: 'w-full mt-1' },
        { children: tCommon('component.save'), variant: 'primary', form: FORM_ID, className: 'w-full mt-1' },
      ]}
      open={open}
      onClose={onClose}
      body={
        <div className='flex flex-col gap-6'>
          <div className='flex flex-col gap-1'>
            <h1 className='font-medium text-lg '>{tCommon('my-page.edit_education_title')}</h1>
            <p className='text-sm text-slate-500'>{tCommon('my-page.edit_education_label')}</p>
          </div>
          <form id={FORM_ID} onSubmit={handleSubmit(onSubmit)} className='flex flex-col gap-6'>
            <FormField
              className=' flex-col  '
              type='input'
              label={tForm('label.affiliation')}
              componentProps={{
                name: 'name',
                control,
                required: true,
                placeholder: 'e.g., ABC Company',
              }}
            />
            <FormField
              className=' flex-col '
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
                  return prevDates; // Return the previous state if no change is detected
                });
              }}
            />
          </form>
        </div>
      }
    />
  );
};

export default EditExperienceModal;
