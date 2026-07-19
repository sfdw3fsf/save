import FormField from '@/components/core/form/form-field/FormField';
import Modal from '@/components/core/modal/Modal';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import MonthYearPicker from './MonthYearPicker';

type EditEducationModalProps = {
  open: boolean;
  onClose: () => void;
  handleEditEducation: (updatedEducation: any) => void;
  handleDeleteEducation: (id: string | number) => void;
  initialValues: {
    id: string | number;
    name: string;
    detail: string;
    entranceMonth?: number;
    entranceYear?: number;
    graduationMonth?: number;
    graduationYear?: number;
  };
};

type EditEducationFormValue = {
  name: string;
  detail: string;
};

const EditEducationModal = ({
  open,
  onClose,
  handleEditEducation,
  initialValues,
  handleDeleteEducation,
}: EditEducationModalProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const FORM_ID = 'edit_education_modal';

  const [dates, setDates] = useState({
    entranceMonth: initialValues.entranceMonth,
    entranceYear: initialValues.entranceYear,
    graduationMonth: initialValues.graduationMonth,
    graduationYear: initialValues.graduationYear,
  });

  const { control, handleSubmit, reset } = useForm<EditEducationFormValue>({
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
        entranceMonth: initialValues.entranceMonth,
        entranceYear: initialValues.entranceYear,
        graduationMonth: initialValues.graduationMonth,
        graduationYear: initialValues.graduationYear,
      });
    }
  }, [initialValues, reset, setDates]);

  const onSubmit = (data: EditEducationFormValue) => {
    const educationWithDates = {
      id: initialValues.id,
      name: data.name,
      detail: data.detail,
      entranceMonth: dates.entranceMonth,
      entranceYear: dates.entranceYear,
      graduationMonth: dates.graduationMonth,
      graduationYear: dates.graduationYear,
    };
    handleEditEducation(educationWithDates);
    onClose();
  };
  const onDelete = () => {
    handleDeleteEducation(initialValues.id);
    onClose();
  };
  return (
    <Modal
      isDivider={false}
      header={
        <div className='flex flex-col gap-1'>
          <h1 className='font-medium text-lg '>{tCommon('my-page.edit_education_title')}</h1>
          <p className='text-sm text-slate-500'>{tCommon('my-page.edit_education_label')}</p>
        </div>
      }
      className=''
      buttons={[
        { children: tCommon('my-page.delete_action'), onClick: onDelete, className: 'w-full mt-1' },
        { children: tCommon('component.save'), variant: 'primary', form: FORM_ID, className: 'w-full mt-1' },
      ]}
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
                placeholder: 'e.g., ABC University',
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
                placeholder: 'e.g., Doctor of Obstetrics and Gynecology',
              }}
            />
            <MonthYearPicker
              initialStartMonth={initialValues.entranceMonth}
              initialStartYear={initialValues.entranceYear}
              initialEndMonth={initialValues.graduationMonth}
              initialEndYear={initialValues.graduationYear}
              onDateChange={(startMonth, startYear, endMonth, endYear) => {
                if (
                  dates.entranceMonth !== startMonth ||
                  dates.entranceYear !== startYear ||
                  dates.graduationMonth !== endMonth ||
                  dates.graduationYear !== endYear
                ) {
                  setDates({
                    entranceMonth: startMonth,
                    entranceYear: startYear,
                    graduationMonth: endMonth,
                    graduationYear: endYear,
                  });
                }
              }}
            />
          </form>
        </div>
      }
    />
  );
};

export default EditEducationModal;
