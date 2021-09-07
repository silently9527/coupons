import config from '@/utils/config'
import md5 from 'md5'

export const password = {
  encryption (password) {
    if (config.getPasswordEncryption()) {
      return md5(password)
    } else {
      return password
    }
  }
}
