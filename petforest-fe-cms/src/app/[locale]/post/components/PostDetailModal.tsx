import CustomIconButton from '@/components/common/CustomIconButton';
import CustomSegmented from '@/components/common/CustomSegmented';
import CustomFormInput from '@/components/common/form/CustomFormField';
import LabelForm from '@/components/common/form/LabelForm';
import CustomText from '@/components/common/Text';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import useResize from '@/hook/useResize';
import { PostDetailDTO } from '@/store/post/dto/post.dto';
import { COLORS } from '@/theme';
import { POST_CATEGORIES } from '@/utils/constants/post/post.constant';
import { InputEnum } from '@/utils/enums/input.enum';
import { POST_EXPOSURE, POST_MAIN_CATEGORIES, POST_VISIBILITY } from '@/utils/enums/post/post.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { helperOption } from '@/utils/helpers/common.helper';
import { InfoCircleOutlined, PaperClipOutlined } from '@ant-design/icons';
import { Col, Radio, Row } from 'antd';
import { useEffect, useMemo } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

type PostDetailModalProps = {
  postDetailData?: PostDetailDTO;
};

const PostDetailModal = ({ postDetailData }: PostDetailModalProps) => {
  const { t: tPost } = useTranslation('post');
  const { t: tEnum } = useTranslation('enum');
  const { isResponsive } = useResize();
  const { control, watch, reset, setValue } = useFormContext<PostDetailDTO>();
  const { canEdit } = useMenuPermission(MenuKeys.POST);

  const categoryWatch = watch('categories.category');
  const categoryOptions = helperOption('post.categories', POST_MAIN_CATEGORIES, tEnum);

  const POST_EXPOSURE_OPTIONS = Object.keys(POST_EXPOSURE).map((item) => ({
    label: tEnum(`post.exposure.${item}` as any),
    value: item,
  }));

  const subCategoryOptions = useMemo(() => {
    if (!categoryWatch) return undefined;
    const subCategory = POST_CATEGORIES[categoryWatch as keyof typeof POST_CATEGORIES];
    if (categoryWatch !== 'JOURNAL') {
      return typeof subCategory === 'object' ? helperOption('post.categories', subCategory as any, tEnum) : undefined;
    }
    return typeof subCategory === 'object' ? helperOption('post.categories', subCategory as any, tEnum) : undefined;
  }, [categoryWatch, tEnum]);

  useEffect(() => {
    if (categoryWatch !== 'JOURNAL') {
      setValue('thumbnail', []);
    }
  }, [categoryWatch, setValue]);

  const colSpan = useMemo(() => {
    return isResponsive ? 24 : 12;
  }, [isResponsive]);

  useEffect(() => {
    if (!postDetailData) return;

    reset(postDetailData);
  }, [postDetailData, reset]);

  return (
    <div className='mt-4'>
      <Row gutter={[24, 16]}>
        <Col span={24}>
          <Controller
            name='visibility'
            control={control}
            render={({ field: { value, onChange } }) => (
              <LabelForm label={tPost('detail.public_status')} className={` gap-[8px]`} classNameLabel={`font-medium`}>
                <CustomSegmented
                  disabled={!canEdit}
                  options={POST_VISIBILITY}
                  path='post.visibility'
                  value={value}
                  onChange={onChange}
                  label={tPost('detail.public_status')}
                />
              </LabelForm>
            )}
          />
        </Col>
        <Col span={24}>
          <CustomFormInput
            readOnly={!canEdit}
            control={control}
            name='title'
            type={InputEnum.TEXT}
            label={tPost('detail.title')}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            disabled
            control={control}
            name='createdAt'
            type={InputEnum.DATE_PICKER}
            label={tPost('detail.date')}
            showTime
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            disabled={!canEdit}
            control={control}
            name='categories.category'
            optionItems={categoryOptions}
            type={InputEnum.SELECT}
            label={tPost('detail.category')}
          />
        </Col>
        {categoryWatch === 'PRESS_REPORT' ? (
          <>
            <Col span={24}>
              <CustomFormInput control={control} name='link' type={InputEnum.TEXT} label={tPost('detail.link')} />
            </Col>
            <Col span={24}>
              <Controller
                name='exposure'
                render={({ field: { value, onChange } }) => (
                  <LabelForm label={tPost('detail.exposure')} className={` gap-[8px]`} classNameLabel={`font-medium`}>
                    <Radio.Group
                      value={value}
                      onChange={(e) => {
                        onChange && onChange(e.target.value);
                      }}>
                      {POST_EXPOSURE_OPTIONS?.map((option) => (
                        <Radio key={option.value as any} value={option.value}>
                          {option.label}
                        </Radio>
                      ))}
                    </Radio.Group>
                  </LabelForm>
                )}
              />
              <div className='flex items-center gap-[6px] mt-[18px]'>
                <InfoCircleOutlined style={{ color: COLORS.neutral[70] }} />
                <CustomText variant='caption-1' className='text-neutral-70 font-medium'>
                  {tPost('detail.description')}
                </CustomText>
              </div>
            </Col>
          </>
        ) : (
          <>
            {subCategoryOptions && (
              <Col span={24}>
                <CustomFormInput
                  disabled={!canEdit}
                  control={control}
                  name='categories.subCategory'
                  optionItems={subCategoryOptions}
                  type={InputEnum.SELECT}
                  label={tPost('detail.sub_category')}
                />
              </Col>
            )}
            <Col span={24}>
              <CustomFormInput
                name='attachments'
                control={control}
                disabled={!canEdit}
                buttonUpload={<CustomIconButton icon={<PaperClipOutlined className='!text-neutral-70' />} />}
                type={InputEnum.FILE}
                multiple={true}
                bordered={true}
                label={tPost('detail.attach')}
              />
            </Col>

            {categoryWatch === 'JOURNAL' && (
              <Col span={24}>
                <CustomFormInput
                  name='thumbnail'
                  control={control}
                  disabled={!canEdit}
                  buttonUpload={<CustomIconButton icon={<PaperClipOutlined className='!text-neutral-70' />} />}
                  type={InputEnum.IMAGE}
                  multiple={false}
                  bordered={true}
                  label={tPost('detail.thumbnail')}
                  fileType='image'
                  maxCount={1}
                  displayName={false}
                />
              </Col>
            )}

            <Col span={24}>
              <CustomFormInput
                readOnly={!canEdit}
                control={control}
                name='content'
                type={InputEnum.QUILL}
                bordered={true}
                label={tPost('detail.content')}
              />
            </Col>
          </>
        )}
      </Row>
    </div>
  );
};

export default PostDetailModal;
