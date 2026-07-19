import FormField from '@/components/core/form/form-field/FormField';
import Modal from '@/components/core/modal/Modal';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import { userExpertUpdateAPI } from '@/data/user/user.api';
import i18n from '@/i18n';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { enqueueSnackbar } from 'notistack';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

type BusinessProps = {
  open: boolean;
  onClose: () => void;
  advisories?: any;
  business?: any;
  userDetail?: any;
};

type FormValues = {
  businessFieldIds: { [key: string]: number[] };
  advisoryFieldIds: { [key: string]: number[] };
};

const calculateDefaultValues = (business: any, advisories: any): FormValues => ({
  businessFieldIds: business?.reduce((acc: any, category: any) => {
    acc[category.id] = category.businessFields.filter((field: any) => field.isUserSelected).map((field: any) => field.id);
    return acc;
  }, {}),
  advisoryFieldIds: advisories?.reduce((acc: any, category: any) => {
    acc[category.id] = category.advisoryFields.filter((field: any) => field.isUserSelected).map((field: any) => field.id);
    return acc;
  }, {}),
});

const EditBusinessAdvisoryModal = ({ open, onClose, advisories, business, userDetail }: BusinessProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const FORM_ID = 'edit_business_advisory_form';
  const defaultValues = calculateDefaultValues(business, advisories);

  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormValues>({
    defaultValues,
    mode: 'onSubmit',
  });

  const queryClient = useQueryClient();
  const { mutate: updateUser } = useMutation({
    mutationFn: (request: any) => userExpertUpdateAPI(request),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.USER_DETAILS] });
      SuccessToast({
        label: tCommon('my-page.success_edit_profile_label'),
        content: tCommon('my-page.success_edit_profile_content'),
      });
    },
  });

  useEffect(() => {
    reset(defaultValues);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [business, advisories, reset]);

  const onSubmit = (data: FormValues) => {
    const selectedBusinessFields = Object.values(data.businessFieldIds)
      .flat()
      .filter((fieldId) => typeof fieldId === 'number');
    const selectedAdvisoryFields = Object.values(data.advisoryFieldIds)
      .flat()
      .filter((fieldId) => typeof fieldId === 'number');

    const businessSum = Object.values(data.businessFieldIds)
      .map((item) => item.length)
      .reduce((total, item) => total + item);

    const advisorySum = Object.values(data.advisoryFieldIds)
      .map((item) => item.length)
      .reduce((total, item) => total + item);

    console.log(businessSum);
    console.log(advisorySum);
    if (!(businessSum === 2) || !(advisorySum >= 1)) {
      enqueueSnackbar({ variant: 'error', message: tCommon('my-page.business_validate') });

      return;
    }

    const { fullName: name, school: schoolFrom, affiliation, department, detailedField } = userDetail;
    updateUser({
      name,
      schoolFrom,
      affiliation,
      department,
      detailedField,
      businessFieldIds: selectedBusinessFields,
      advisoryFieldIds: selectedAdvisoryFields,
    });
    onClose();
  };

  return (
    <Modal
      className=''
      buttons={[{ children: tCommon('component.save'), variant: 'primary', className: 'w-full', form: FORM_ID, type: 'submit' }]}
      open={open}
      onClose={onClose}
      body={
        <div className='flex flex-col gap-6'>
          <div className='flex flex-col gap-1'>
            <h1 className='font-medium text-lg text-primary-500'>{tCommon('sign_up.business_field_section')}</h1>
          </div>
          <form id={FORM_ID} onSubmit={handleSubmit(onSubmit)} className='flex flex-col gap-6'>
            {business?.map((category: any, index: number) => (
              <div key={`business-category-${index}`} className='flex flex-col gap-3'>
                <h3 className='font-medium'>{i18n.language === 'en' ? category.nameInEnglish : category.name}</h3>
                <FormField
                  label=''
                  type='option-group'
                  componentProps={{
                    name: `businessFieldIds.${category.id}` as const,
                    control,
                    replace: true,
                    required: true,
                    type: 'checkbox',
                    values: category.businessFields.map((field: any) => ({
                      label: i18n.language === 'en' ? field.nameInEnglish : field.name,
                      checkedValue: field.id,
                    })),
                  }}
                />
                {errors.businessFieldIds?.[category.id] && (
                  <span className='text-red-500 text-xs'>Please select at least 2 options</span>
                )}
              </div>
            ))}

            <h1 className='font-medium text-lg text-primary-500'>{tCommon('sign_up.main_advisory_areas')}</h1>
            {advisories?.map((category: any, index: number) => (
              <div key={`advisory-category-${index}`} className='flex flex-col gap-3'>
                <h3 className='font-medium'>{i18n.language === 'en' ? category.nameInEnglish : category.name}</h3>
                <FormField
                  label=''
                  type='option-group'
                  componentProps={{
                    name: `advisoryFieldIds.${category.id}` as const,
                    control,
                    type: 'checkbox',
                    replace: true,
                    required: true,
                    values: category.advisoryFields.map((field: any) => ({
                      label: i18n.language === 'en' ? field.nameInEnglish : field.name,
                      checkedValue: field.id,
                    })),
                  }}
                />
                {errors.advisoryFieldIds?.[category.id] && (
                  <span className='text-red-500 text-xs'>Please select at least 1 option</span>
                )}
              </div>
            ))}
          </form>
        </div>
      }
    />
  );
};

export default EditBusinessAdvisoryModal;
