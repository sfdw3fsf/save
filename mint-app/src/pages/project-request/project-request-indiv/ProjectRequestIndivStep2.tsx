import Block from '@/components/core/block/Block';
import Button from '@/components/core/button/Button';
import Drawer from '@/components/core/drawer/Drawer';
import { DropdownItem } from '@/components/core/dropdown/Dropdown';
import FormField from '@/components/core/form/form-field/FormField';
import FormLabel from '@/components/core/form/form-label/FormLabel';
import FormMessage, { FormMessageTranslateKey } from '@/components/core/form/form-message/FormMessage';
import ScheduleCalendar, { TScheduleEvent } from '@/components/core/schedule-calendar/ScheduleCalendar';
import CalendarBlankIcon from '@/components/icons/CalendarBlankIcon';
import PencilSimpleLineIcon from '@/components/icons/PencilSimpleLineIcon';
import { ADVISORY_TYPE } from '@/utils/enums/project/advisory.enum';
import { ProjectRequestStep2Input, projectRequestStep2Schema } from '@/utils/schemas/project-request.schema';
import { IMAGES } from '@/utils/theme';
import { yupResolver } from '@hookform/resolvers/yup';
import dayjs from 'dayjs';
import { useEffect, useMemo, useRef, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import ProjectRequestBusinessModal, {
  ProjectBusinessCombineInput,
  ProjectRequestBusinessModalRef,
} from './business-field-register/ProjectRequestBusinessModal';
import BlockContent from './components/BlockContent';
import InfoSection from './components/InfoSection';
import { combineDates } from './helpers/combine-dates.helpers';
import { useCombineFields } from './hooks/useCombineFields';
import { ProjectRequestData, ProjectRequestIndivProps } from './ProjectRequestIndivPage';

const ProjectRequestIndivStep2 = ({ formID, onNext, initialValues }: ProjectRequestIndivProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  // REFS
  const businessModalRef = useRef<ProjectRequestBusinessModalRef | null>(null);

  // STATES
  const [projectBusinessCombineInput, setProjectBusinessCombineInput] = useState<ProjectBusinessCombineInput>();
  const [openScheduleCalendar, setOpenScheduleCalendar] = useState(false);
  const [meetings, setMeetings] = useState<TScheduleEvent[] | []>([]);
  const nextDate = useMemo(() => dayjs().add(1, 'd').toDate(), []);

  // FORMS
  const {
    control,
    handleSubmit,
    setValue,
    formState: { errors },
    reset,
  } = useForm<ProjectRequestStep2Input>({
    resolver: yupResolver<ProjectRequestStep2Input>(projectRequestStep2Schema),
    defaultValues: projectRequestStep2Schema.getDefault(),
  });

  // HOOKS
  const {
    businessFieldCategories,
    advisoryFieldCategories,
    medicalFieldCategories,
    isFetchingBusiness,
    isFetchingAdvisory,
    isFetchingMedical,
    filteredBusinessField,
    filteredAdvisoryField,
    filteredMedicalField,
  } = useCombineFields(projectBusinessCombineInput);

  // METHODS
  const onSubmit = handleSubmit(async (data: ProjectRequestStep2Input) => {
    onNext?.({ ...(data as unknown as Partial<ProjectRequestData>), projectBusinessCombineInput, meetings });
  });

  // CONTANTS
  const ADVISORIES = useMemo<DropdownItem[]>(
    () =>
      Object.keys(ADVISORY_TYPE).map((item) => ({
        label: tCommon(`project_request.${item}` as any),
        value: item,
      })),
    [tCommon],
  );

  // EFFECTS
  useEffect(() => {
    setValue('selectedBusinessFields', !!projectBusinessCombineInput);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [projectBusinessCombineInput]);

  useEffect(() => {
    setValue('selectedMeetings', !!meetings?.length);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [meetings.length]);

  useEffect(() => {
    if (initialValues) {
      setProjectBusinessCombineInput(initialValues.projectBusinessCombineInput);
      setMeetings(initialValues.meetings || []);
      reset(initialValues as unknown as ProjectRequestStep2Input);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialValues]);

  const consultantDates = useMemo(() => combineDates(meetings), [meetings]);

  return (
    <>
      <div className='flex flex-col md:mt-1.5'>
        <div className='font-medium text-md'>{tCommon('project_request.step_2.title')}</div>
        <div className='text-slate-500 mt-0.5 mb-8 max-md:mb-6'>{tCommon('project_request.step_2.detailed_description')}</div>
        <form id={formID} className='flex flex-col gap-8 max-md:gap-6' onSubmit={onSubmit}>
          <div className='w-full'>
            <Block
              isError={!!errors.selectedBusinessFields}
              imgSrc={IMAGES.ILLUSTRATION.BROKEN}
              header={{
                title: tCommon('project_request.business_advisory_field'),
                rightNode: projectBusinessCombineInput ? (
                  <Button
                    isLoading={isFetchingAdvisory || isFetchingBusiness || isFetchingMedical}
                    leftIcon={<PencilSimpleLineIcon />}
                    sizing='2sm'
                    className='h-7'
                    onClick={() => businessModalRef.current?.setOpen(true)}>
                    {tCommon('component.edit')}
                  </Button>
                ) : undefined,
              }}
              message={tCommon('project_request.for_better_matching')}
              buttonProps={{
                children: tCommon('project_request.select_business_area'),
                onClick: () => businessModalRef.current?.setOpen(true),
              }}>
              {!projectBusinessCombineInput ? null : (
                <div className='flex w-full flex-col gap-5 text-sm text-left'>
                  {filteredBusinessField.length > 0 && (
                    <BlockContent title={tForm('label.business_area_consult_on')} content={filteredBusinessField} />
                  )}
                  {filteredAdvisoryField.length > 0 && (
                    <BlockContent title={tForm('label.main_advisory')} content={filteredAdvisoryField} />
                  )}
                  {filteredMedicalField.length > 0 && (
                    <BlockContent title={tForm('label.medical_field')} content={filteredMedicalField} />
                  )}
                </div>
              )}
            </Block>
          </div>
          <div className='grid grid-cols-2 gap-x-6 gap-y-8 max-md:grid-cols-1 max-md:gap-y-4'>
            <FormLabel label={tCommon('project_request.project_name')} required />
            <FormField
              type='input'
              componentProps={{
                required: true,
                control,
                placeholder: tForm('placeholder.project_name'),
                name: 'name',
              }}
              className='max-md:mb-2'
            />
            <div className='flex flex-col w-full gap-1'>
              <FormLabel label={tCommon('project_request.advisory_type')} required />
              <InfoSection content={tCommon('project_request.advisory_info_1')} />
              <InfoSection content={tCommon('project_request.advisory_info_2')} />
            </div>

            <FormField
              type='drop-down'
              componentProps={{
                required: true,
                control,
                placeholder: tCommon('project_request.advisory_field_placeholder'),
                name: 'advisoryType',
                items: ADVISORIES,
                className: 'py-1',
              }}
              className='max-md:mb-2'
            />

            <FormLabel label={tCommon('project_request.desired_consultant_date')} required />
            <div className='flex flex-col'>
              <Button
                className={twMerge(
                  'w-full max-md:mb-2 py-3 hover:border-primary-500 hover:bg-slate-50 text-md',
                  consultantDates ? 'font-medium' : 'font-normal',
                  !!errors.selectedMeetings && 'border-comp-border-danger-strong',
                )}
                rightIcon={<CalendarBlankIcon />}
                labelContStyle='w-full justify-between'
                onClick={() => setOpenScheduleCalendar(true)}
                labelStyle={consultantDates ? 'text-slate-800' : 'text-slate-500'}>
                {consultantDates || tForm('placeholder.desired_consultant_date')}
              </Button>
              {!!errors.selectedMeetings && (
                <FormMessage variant='error' translateKey={errors.selectedMeetings.message as FormMessageTranslateKey} />
              )}
            </div>
            <FormLabel label={tCommon('project_request.advisory_time')} />
            <FormField
              type='input'
              componentProps={{
                required: true,
                control,
                placeholder: tForm('placeholder.advisory_time'),
                name: 'advisoryTime',
              }}
              className='max-md:mb-2'
            />
          </div>
        </form>

        <ProjectRequestBusinessModal
          ref={businessModalRef}
          onSubmit={setProjectBusinessCombineInput}
          businessFieldCategories={businessFieldCategories}
          advisoryFieldCategories={advisoryFieldCategories}
          medicalFieldCategories={medicalFieldCategories}
          defaultValues={projectBusinessCombineInput}
        />
      </div>
      <Drawer
        open={openScheduleCalendar}
        onClose={() => setOpenScheduleCalendar(false)}
        title={tCommon('calendar.set_up_advisory_schedule')}
        childClassName='lg:w-fit md:w-full w-full'>
        <ScheduleCalendar
          events={meetings}
          submitText={tCommon('calendar.complete_request')}
          titleSection={<div className='mx-2 text-slate-500 whitespace-pre-line'>{tCommon('calendar.description')}</div>}
          onCancel={() => setOpenScheduleCalendar(false)}
          onSubmit={(value) => {
            setMeetings(value);
            setOpenScheduleCalendar(false);
          }}
          defaultDate={meetings?.length === 0 ? nextDate : meetings[0]?.start}
          variant='mixed'
          allowPast={false}
          isShowLegend={false}
          isDivider={true}
        />
      </Drawer>
    </>
  );
};

export default ProjectRequestIndivStep2;
