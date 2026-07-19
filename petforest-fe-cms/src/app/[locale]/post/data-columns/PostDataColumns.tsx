import CustomIconButton from '@/components/common/CustomIconButton';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { CategoryDTO, PostDTO } from '@/store/post/dto/post.dto';
import { PostUpdateREQ } from '@/store/post/request/post.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { POST_MAIN_CATEGORIES, POST_VISIBILITY } from '@/utils/enums/post/post.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { helperFilter } from '@/utils/helpers/common.helper';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import clsx from 'clsx';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type PostDataColumnsProps = {
  onDetail: (id: number) => void;
  onUpdatePost: (id: number, values: PostUpdateREQ) => void;
};

export const PostDataColumns = ({ onDetail, onUpdatePost }: PostDataColumnsProps): TableColumnsType<PostDTO> => {
  const { t: tPost } = useTranslation('post');
  const { t: tCommon } = useTranslation('common');
  const { t: tEnum } = useTranslation('enum');
  const { canEdit } = useMenuPermission(MenuKeys.POST);

  return [
    {
      title: <EditableHeader title={tPost('table.title')} />,
      dataIndex: 'title',
      sorter: true,
      width: 500,
      render: (text: string, record) => (
        <CustomTextInput
          readOnly={!canEdit}
          onBlur={handleBlurUpdate<PostUpdateREQ>(record.id, 'title', text, onUpdatePost)}
          type={InputEnum.TEXT}
          defaultValue={text}
        />
      ),
    },
    {
      title: tPost('table.public_status'),
      dataIndex: 'visibility',
      sorter: true,
      width: 100,
      filters: helperFilter('post.visibility', POST_VISIBILITY, tEnum),
      render: (visibility: POST_VISIBILITY) => (
        <div className={clsx(`${visibility === POST_VISIBILITY.RELEASE ? 'text-primary-1000 font-medium' : ''}`)}>
          {tEnum(`post.visibility.${visibility}`)}
        </div>
      ),
    },
    {
      title: tPost('table.category'),
      dataIndex: 'category',
      sorter: true,
      width: 100,
      filters: helperFilter('post.categories', POST_MAIN_CATEGORIES, tEnum),
      render: (category: CategoryDTO) => (category?.category ? tEnum(`post.categories.${category.category}`) : ''),
    },
    {
      title: tPost('table.date'),
      dataIndex: 'createdAt',
      sorter: true,
      width: 100,
    },
    {
      title: tCommon('table.view'),
      className: 'ant-table-view-column',
      key: 'look',
      width: 50,
      align: 'center',
      fixed: 'right',
      render: (_, record) => (
        <CustomIconButton
          onClick={() => {
            onDetail(record.id);
          }}
          icon={<Image src={ICONS.LOOK} alt='Drag' width={24} height={24} />}
        />
      ),
    },
  ];
};
