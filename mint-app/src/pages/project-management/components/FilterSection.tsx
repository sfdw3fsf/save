import Button from '@/components/core/button/Button';
import Divider from '@/components/core/divider/Divider';
import FormField from '@/components/core/form/form-field/FormField';
import i18n from '@/i18n';
import { useAuthStore } from '@/store/auth.store';
import { PROJECT_STATICS_ENUM, PROJECT_STATUS } from '@/utils/enums/project/project-list.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { FilterProjectInput, filterProjectSchema } from '@/utils/schemas/project-request.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useEffect, useMemo } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

const FORM_ID = 'filter-project';
type FilterSectionProps = {
  initialValues?: FilterProjectInput | null;
  onSave?: (data: FilterProjectInput) => void;
  onCancel?: () => void;
  onClear?: () => void;
};

const FilterSection = ({ initialValues, onSave, onCancel, onClear }: FilterSectionProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { control, handleSubmit, reset } = useForm<FilterProjectInput>({
    resolver: yupResolver<FilterProjectInput>(filterProjectSchema),
    defaultValues: filterProjectSchema.getDefault(),
  });

  const language = i18n.language;

  const { userInfo } = useAuthStore();

  const items = useMemo(
    () =>
      userInfo?.userRole === USER_ROLE.MEMBER
        ? Object.keys(PROJECT_STATUS)
        : [PROJECT_STATUS.MATCHING_COMPLETED, PROJECT_STATUS.PROJECT_IN_PROGRESS, PROJECT_STATUS.PROJECT_COMPLETED],
    [userInfo?.userRole],
  );

  const onSubmit = handleSubmit(async (data: FilterProjectInput) => {
    onSave?.(data);
  });

  useEffect(() => {
    if (initialValues) {
      reset(initialValues);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className='flex flex-col w-full h-[calc(100vh-84px)]'>
      <form id={FORM_ID} className='flex flex-col gap-4 px-6 py-4 overflow-y-auto h-full static-scrollbar' onSubmit={onSubmit}>
        <span className='text-black text-lg'>{tCommon('project_management.processing_status')}</span>
        <FormField
          type='option-group'
          componentProps={{
            control,
            type: 'checkbox',
            name: 'status',
            replace: true,
            values: items?.map((status) => {
              const label =
                userInfo?.userRole === USER_ROLE.EXPERT && status === PROJECT_STATICS_ENUM.PROJECT_COMPLETED
                  ? tCommon('project_management.project_completed_status_expert')
                  : tCommon(`project_management.${status}` as any);
              return {
                label,
                checkedValue: status,
              };
            }),
          }}
        />
        {/* Calendar */}
        <div className='mt-4 flex flex-col gap-4 mb-2'>
          <span className='text-black text-lg'>{tCommon('project_management.advisory_request_date_time')}</span>
          <FormField
            type='date-range-picker'
            componentProps={{
              control,
              name: 'advisoryDate',
              placeholder: tCommon('project_management.advisory_request_date_time_placeholder'),
            }}
          />
        </div>
      </form>

      <div className={'flex gap-2 items-center px-6 pb-6 mt-auto flex-col w-full'}>
        <Divider />
        <div className={twMerge('flex gap-3 justify-between w-full py-2 ', language === 'en' && 'flex-wrap sm:flex-nowrap')}>
          <Button
            className='w-full'
            variant='grayBox'
            onClick={() => {
              reset(filterProjectSchema.getDefault());
              onClear?.();
            }}>
            {tCommon('project_management.clear_filter')}
          </Button>
          <Button className='w-full' onClick={onCancel}>
            {tCommon('button.cancel')}
          </Button>
          <Button className='w-full' variant='primary' form={FORM_ID}>
            {tCommon('button.save')}
          </Button>
        </div>
      </div>
    </div>
  );
};

export default FilterSection;
